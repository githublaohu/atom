/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.atom.service.operator.consumers.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.lamp.atom.service.domain.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.ScheduleReturn;
import com.lamp.atom.schedule.api.deploy.AtomInstances;
import com.lamp.atom.schedule.api.deploy.Deploy;
import com.lamp.atom.schedule.api.strategy.ScheduleStrategyType;
import com.lamp.atom.schedule.api.strategy.Strategy;
import com.lamp.atom.schedule.core.AtomScheduleService;
import com.lamp.atom.schedule.python.operator.CreateOperator;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.atom.service.operator.domain.SourceAndConnect;
import com.lamp.atom.service.operator.domain.TaskParam;
import com.lamp.atom.service.operator.entity.ConnectionEntity;
import com.lamp.atom.service.operator.entity.DataSourceEntity;
import com.lamp.atom.service.operator.entity.ModelEntity;
import com.lamp.atom.service.operator.entity.NodeEntity;
import com.lamp.atom.service.operator.entity.OperatorEntity;
import com.lamp.atom.service.operator.entity.OrganizationEntity;
import com.lamp.atom.service.operator.entity.ResourceRelationEntity;
import com.lamp.atom.service.operator.entity.RuntimeEntity;
import com.lamp.atom.service.operator.entity.ServiceInfoEntity;
import com.lamp.atom.service.operator.service.ConnectionService;
import com.lamp.atom.service.operator.service.DataSourceService;
import com.lamp.atom.service.operator.service.ModelService;
import com.lamp.atom.service.operator.service.NodeService;
import com.lamp.atom.service.operator.service.OperatorService;
import com.lamp.atom.service.operator.service.OrganizationService;
import com.lamp.atom.service.operator.service.ResourceRelationService;
import com.lamp.atom.service.operator.service.RuntimeService;
import com.lamp.atom.service.operator.service.ServiceInfoService;
import com.lamp.decoration.core.result.ResultObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/taskEvent")
@RestController("taskEventController")
@Api(tags = {"任务事件操作接口"})
public class TaskEventController {

    @Reference
    private NodeService nodeService;
    @Reference
    private OperatorService operatorService;
    @Reference
    private ModelService modelService;
    @Reference
    private OrganizationService organizationService;
    @Reference
    private ServiceInfoService serviceInfoService;
    @Reference
    private DataSourceService dataSourceService;
    @Reference
    private ConnectionService connectionService;
    @Reference
    private RuntimeService runtimeService;
    @Reference
    private ResourceRelationService resourceRelationService;

    @Autowired
    private AtomScheduleService atomScheduleService;

    @Value("${nacos.config.server-addr}")
    private String nacosAddr;
    @Value("${nacos.config.namespace}")
    private String namespace;
    NamingService namingService = null;

    /**
     * 获取nacos连接
     * @throws NacosException
     */
    @PostConstruct
    public void init() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", this.nacosAddr);
        properties.put("namespace", this.namespace);
        namingService = NamingFactory.createNamingService(properties);
    }

    /**
     * console：开始实验任务
     *
     * @param taskParam 实验任务参数
     * @return
     */
    @PostMapping("/startExamTask")
    public ResultObject<String> startExamTask(@RequestBody TaskParam taskParam) {
        OrganizationEntity organization = organizationService.queryOrganizationById(taskParam.getTaskId());
        // 1、根据节点关系查询出实验ID下的所有节点
        ResourceRelationEntity nodeRelationEntity = new ResourceRelationEntity();
        nodeRelationEntity.setRelationType(RelationType.NODE_RELATION);
        nodeRelationEntity.setBeRelatedId(organization.getId());
        nodeRelationEntity.setBeRelatedType(ResourceType.ORGANIZATION);
        List<ResourceRelationEntity> nodeRelations = resourceRelationService.queryResourceRelationEntitys(nodeRelationEntity);

        // 2、根据资源关系查出依赖表
        List<Schedule> scheduleList = new ArrayList<>();
        List<RuntimeEntity> runtimeList = new ArrayList<>();
        ResourceRelationEntity resourceRelationEntity = new ResourceRelationEntity();
        resourceRelationEntity.setRelationType(RelationType.RESOURCE_RELATION);
        resourceRelationEntity.setRelateType(ResourceType.NODE);

        // 遍历Node
        for (ResourceRelationEntity nodeRelation : nodeRelations) {
            NodeEntity node = new NodeEntity();
            node.setId(nodeRelation.getRelateId());
            node = nodeService.queryNodeEntity(node);

            // 判断节点状态是否为“编辑完成”
            if (node.getNodeStatus() != NodeStatus.EDIT_FINISH) {
                log.info("节点状态校验失败 {}", node);
                return ResultObjectEnums.NODE_STATUS_CHECK_FAIL.getResultObject();
            }

            resourceRelationEntity.setRelateId(node.getId());
            List<ResourceRelationEntity> resourceRelations = resourceRelationService.queryResourceRelationEntitys(resourceRelationEntity);

            // 3、封装Schedule数据和Runtime数据
            Schedule schedule = new Schedule();
            Map<String, String> hardwareConfig = new HashMap<String, String>();
            schedule.setHardwareConfig(hardwareConfig);
            Map<String, String> limits = new HashMap<String, String>();
            schedule.setLimits(limits);
            schedule.setRunParameter(taskParam.getRunParameter());
            schedule.setEnvs(taskParam.getEnvs());

            Strategy strategy = new Strategy();
            Map<String, String> labelMap = new HashMap<>();
            schedule.setNodeId(node.getId());
            schedule.setNodeName(node.getNodeName());
            schedule.setStrategy(strategy);
            schedule.setLabels(labelMap);
            strategy.setScheduleStrategyType(ScheduleStrategyType.DEFAULT_LABEL);
            strategy.setTiming("0");
            //todo 策略的标签

            RuntimeEntity runtime = new RuntimeEntity();
            runtime.setSpaceId(node.getSpaceId());
            runtime.setCaseSourceType(CaseSourceType.NODE);
            runtime.setNodeId(node.getId());
            runtime.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
            runtime.setStartTime(new Date());
            runtime.setEstimateStartTime(new Date());
            //todo runtime的启动/关闭人需要用户管理模块的字段

            for (ResourceRelationEntity resourceRelation : resourceRelations) {
                //服务配置信息
                if (resourceRelation.getBeRelatedType() == ResourceType.SERVICE_INFO) {
                    ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                    hardwareConfig.put("cpu", String.valueOf(serviceInfo.getSiCpu()));
                    hardwareConfig.put("gpu", String.valueOf(serviceInfo.getSiGpu()));
                    hardwareConfig.put("memory", String.valueOf(serviceInfo.getSiDisplayMemory()));
                    hardwareConfig.put("displayMemory", String.valueOf(serviceInfo.getSiDisplayMemory()));
                    labelMap.put(serviceInfo.getSiName(), serviceInfo.getSiLabel());

                    runtime.setLabel(serviceInfo.getSiLabel());
                }
                //服务最大配置信息
                if (resourceRelation.getBeRelatedType() == ResourceType.MAX_SERVICE_INFO) {
                    ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                    limits.put("max_service_config", JSON.toJSONString(serviceInfo));
                }
                //服务最小配置信息
                if (resourceRelation.getBeRelatedType() == ResourceType.MIN_SERVICE_INFO) {
                    ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                    limits.put("min_service_config", JSON.toJSONString(serviceInfo));
                }
                if (resourceRelation.getBeRelatedType() == ResourceType.DATASOURCE) {
                    runtime.setServiceInfoId(resourceRelation.getBeRelatedId());
                }
            }

            scheduleList.add(schedule);
            runtimeList.add(runtime);
        }

        // 4、保存Runtime信息
        runtimeService.batchInsertRuntimeEntity(runtimeList);
        // 5、开启调度
        ScheduleReturn scheduleReturn = new ScheduleReturn();
        for (Schedule schedule : scheduleList) {
            try {
                scheduleReturn = atomScheduleService.createOperators(schedule);
                if (scheduleReturn.getScheduleStatus() != 200) {
                    throw new Exception("createOperators exception!(调度异常)");
                }
                log.error("createOperators success!(调度成功)");
            } catch (Exception e) {
                // 调度失败
                runtimeService.batchUpdateRuntimeEntity(runtimeList);
                log.error("createOperators exception!(调度异常)");
            }
        }
        updateRuntimeStatus(runtimeList, scheduleReturn.getScheduleStatus());

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * console：停止实验任务
     *
     * @param taskParam 实验任务参数
     * @return
     */
    @PostMapping("/stopExamTask")
    public ResultObject<String> stopExamTask(@RequestBody TaskParam taskParam) {
        OrganizationEntity organization = organizationService.queryOrganizationById(taskParam.getTaskId());
        // 1、根据节点关系查询出实验ID下的所有节点
        ResourceRelationEntity nodeRelationEntity = new ResourceRelationEntity();
        nodeRelationEntity.setRelationType(RelationType.NODE_RELATION);
        nodeRelationEntity.setBeRelatedId(organization.getId());
        nodeRelationEntity.setBeRelatedType(ResourceType.ORGANIZATION);
        List<ResourceRelationEntity> nodeRelations = resourceRelationService.queryResourceRelationEntitys(nodeRelationEntity);

        // 2、根据资源关系查出依赖表
        List<Schedule> scheduleList = new ArrayList<>();
        List<RuntimeEntity> runtimeList = new ArrayList<>();
        ResourceRelationEntity resourceRelationEntity = new ResourceRelationEntity();
        resourceRelationEntity.setRelationType(RelationType.RESOURCE_RELATION);
        resourceRelationEntity.setRelateType(ResourceType.NODE);

        // 3、查询Schedule数据和Runtime数据
        for (ResourceRelationEntity nodeRelation : nodeRelations) {
            Schedule schedule = new Schedule();
            schedule.setNodeId(nodeRelation.getRelateId());
            scheduleList.add(schedule);

            RuntimeEntity runtimeEntity = new RuntimeEntity();
            runtimeEntity.setNodeId(nodeRelation.getRelateId());
            List<RuntimeEntity> runtimeEntityList = runtimeService.queryRuntimeEntitys(runtimeEntity);
            runtimeList.addAll(runtimeEntityList);
        }

        // 4、修改Runtime信息
        for (RuntimeEntity runtimeEntity : runtimeList) {
            runtimeEntity.setEndTime(new Date());
            runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.MANUAL_FINISH);
        }
        runtimeService.batchUpdateRuntimeEntity(runtimeList);

        // 5、关闭调度
        for (Schedule schedule : scheduleList) {
            atomScheduleService.uninstallOperators(schedule);
        }

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * console：开始节点任务
     *
     * @param taskParam 节点任务参数
     * @return
     */
    @PostMapping("/startNodeTask")
    @ApiOperation(value = "开始节点任务")
    public ResultObject<String> startNodeTask(@RequestBody TaskParam taskParam) throws NacosException {
        // 0、字段判空&校验
        if (Objects.isNull(taskParam.getTaskId()) ||
                Objects.isNull(taskParam.getOperatorRuntimeType())) {
            log.warn("taskID or operatorRuntimeType must not be null, taskId is {}, operatorRuntimeType is {}",
                    taskParam.getTaskId(), taskParam.getOperatorRuntimeType());
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }

        if (!Objects.equals(OperatorRuntimeType.TRAIN.toString(), taskParam.getOperatorRuntimeType().toString())) {
            log.warn("operatorRuntimeType must be {}, but is {}",
                    OperatorRuntimeType.TRAIN.toString(), taskParam.getOperatorRuntimeType());
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }

        // 1、查出节点信息，判断节点状态是否为“编辑完成”
        NodeEntity node = new NodeEntity();
        node.setId(taskParam.getTaskId());
        node = nodeService.queryNodeEntity(node);

        if (node.getNodeStatus() != NodeStatus.EDIT_FINISH) {
            log.info("节点状态校验失败 {}", node);
            return ResultObjectEnums.NODE_STATUS_CHECK_FAIL.getResultObject();
        }

        // 3、获取调度信息和Runtime信息
        // 包括节点Runtime、算子Runtime、调度Runtime
        List<RuntimeEntity> runtimeEntityList = new ArrayList<>();
        Schedule schedule = getSchedule(taskParam, node, runtimeEntityList);

        // 4、保存Runtime信息
        //todo 返回自增主键ID
        runtimeService.batchInsertRuntimeEntity(runtimeEntityList);

        // 5、开启调度
        ScheduleReturn scheduleReturn = new ScheduleReturn();
        try {
            scheduleReturn = atomScheduleService.createOperators(schedule);
            if (200 != scheduleReturn.getScheduleStatus()) {
                throw new Exception("createOperators exception!(调度异常)");
            }
            log.error("createOperators success!(调度成功)");
        } catch (Exception e) {
            log.error("createOperators exception!(调度异常)");
        }
        updateRuntimeStatus(runtimeEntityList, scheduleReturn.getScheduleStatus());
        runtimeService.batchUpdateRuntimeEntity(runtimeEntityList);

        //todo 更新节点信息（修改时间）

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * console：算子编辑中
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/editing")
    public Integer editStart(@RequestBody RuntimeEntity runtimeEntity) {
        //1、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * console：算子编辑取消
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/editCancel")
    public Integer editCancel(@RequestBody RuntimeEntity runtimeEntity) {
        //1、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDIT_CANCEL);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * console：排队
     */
    @PostMapping("/queuing")
    public Integer queuing(@RequestBody RuntimeEntity runtimeEntity) {
        // 1、调度schedule

        // 2、修改状态
        //runtimeEntity.setOperatorStatus(RuntimeStatus.QUEUING);
        this.atomScheduleService.createOperators(null);
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * console：排队取消中
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/queueCanceling")
    public Integer queueCanceling(@RequestBody RuntimeEntity runtimeEntity) {
        // 1、调度schedule

        // 2、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUE_CANCELING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * schedule 排队取消完成
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/queueCancelFinish")
    public Integer queueCancelFinish(@RequestBody RuntimeEntity runtimeEntity) {
        // 1、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUE_CANCEL);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * console 抢占中
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/occupying")
    public Integer occupying(@RequestBody RuntimeEntity runtimeEntity) {
        // 1、调度schedule

        // 2、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

//    /**
//     * 只有训练的runtime 算子实例创建中
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/caseCreating")
//    public Integer caseCreating(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.CASE_CREATING);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * 只有训练的runtime 算子实例创建完成
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/caseCreateFinish")
//    public Integer caseCreateFinish(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.CASE_CREATE_FINISH);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * 只有训练的runtime 数据下载中
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/dataUploading")
//    public Integer dataUploading(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.DATA_UPLOADING);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * 只有训练的runtime 数据下载完成
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/dataUploadFinish")
//    public Integer dataUploadFinish(@RequestBody OperatorEntity operatorEntity) {
//        //1、修改状态
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.DATA_UPLOAD_FINISH);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }

    /**
     * runtime 算子运行开始
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/running")
    public Integer running(@RequestBody RuntimeEntity runtimeEntity) {
        //1、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAINING);
        Integer status = runtimeService.updateRuntimeEntity(runtimeEntity);
        if (status != 1) {
            return status;
        }
        return 1;
    }

    /**
     * runtime 算子测试
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/testing")
    public Integer testing(@RequestBody RuntimeEntity runtimeEntity) {
        //1、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TESTING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * runtime 算子运行自动完成
     *
     * 训练完成后进行推理
     * 需要：节点ID、模型创建类型
     *
     * @param taskParam
     * @return
     */
    @PostMapping("/runningAutoFinish")
    public ResultObject<String> runningAutoFinish(@RequestBody TaskParam taskParam) throws NacosException {
        // 0、字段判空
        if (Objects.isNull(taskParam.getTaskId()) ||
                Objects.isNull(taskParam.getOperatorRuntimeType())) {
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }

        // 1、修改实例状态（根据节点ID和模型创建类型查出所有runtime)
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.setNodeId(taskParam.getTaskId());
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.AUTO_FINISH);
        Integer status = runtimeService.updateRuntimeStatus(runtimeEntity);
        if (status < 1) {
            return ResultObjectEnums.FAIL.getResultObject();
        }

        // 2、调度schedule（释放资源、保存数据集）
        NodeEntity node = new NodeEntity();
        node.setId(taskParam.getTaskId());
        node = nodeService.queryNodeEntity(node);

        Schedule schedule = getSchedule(taskParam, node, null);
        atomScheduleService.uninstallOperators(schedule);

        // 3、获取下一节点，训练算子完成后启动推理算子
        if (OperatorRuntimeType.TRAIN == taskParam.getOperatorRuntimeType()) {
            // 根据节点查询下一节点的算子
            ResourceRelationEntity nodeRelation = new ResourceRelationEntity();
            nodeRelation.setRelationType(RelationType.RESOURCE_RELATION);
            nodeRelation.setRelateType(ResourceType.NODE);
            nodeRelation.setRelateId(node.getId());
            nodeRelation.setBeRelatedType(ResourceType.NODE);
            nodeRelation = resourceRelationService.queryResourceRelationEntity(nodeRelation);

            ResourceRelationEntity nodeOperatorRelation = new ResourceRelationEntity();
            nodeOperatorRelation.setRelationType(RelationType.RESOURCE_RELATION);
            nodeOperatorRelation.setRelateType(ResourceType.NODE);
            nodeOperatorRelation.setRelateId(nodeRelation.getBeRelatedId());
            nodeOperatorRelation.setBeRelatedType(ResourceType.OPERATOR);
            nodeOperatorRelation = resourceRelationService.queryResourceRelationEntity(nodeOperatorRelation);

            OperatorEntity operator = new OperatorEntity();
            operator.setId(nodeOperatorRelation.getBeRelatedId());
            operator = operatorService.queryOperatorEntity(operator);

            //推理算子，如果算子类型为自动部署，则调用启动节点接口
            if (Objects.equals(operator.getOperatorRuntimeType(), OperatorRuntimeType.REASONING)) {
                DeployType deployType = operator.getDeployType();
                switch (deployType) {
                    case AUTO_DEPLOY:
                        //自动部署（启动节点任务）
                        TaskParam reasonTask = new TaskParam();
                        reasonTask.setTaskId(node.getId());
                        reasonTask.setOperatorRuntimeType(OperatorRuntimeType.REASONING);
                        this.startNodeTask(reasonTask);
                        break;
                    case GREY_DEPLOY:
                        //灰度部署
                        break;
                    case TOUCH_DEPLOY:
                        //触发部署
                        break;
                    case NOT_DEPLOY:
                        //不部署
                        break;
                }
            }
        }

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * console 算子运行手动完成
     *
     * @param taskParam
     * @return
     */
    @PostMapping("/runningFinish")
    @ApiOperation(value = "算子运行手动完成")
    public ResultObject<String> runningFinish(@RequestBody TaskParam taskParam) throws NacosException {
        //0、字段判空
        if (Objects.isNull(taskParam.getTaskId()) ||
                Objects.isNull(taskParam.getOperatorRuntimeType())) {
            return ResultObjectEnums.FAIL.CHECK_PARAMETERS_FAIL.getResultObject();
        }

        // 1、对所有实例修改状态（根据节点ID和模型创建类型查出所有runtime)
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.setNodeId(taskParam.getTaskId());
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.AUTO_FINISH);
        Integer status = runtimeService.updateRuntimeStatus(runtimeEntity);
        if (status < 1) {
            return ResultObjectEnums.FAIL.getResultObject();
        }

        // 2、调度schedule（释放资源、保存数据集）
        NodeEntity node = new NodeEntity();
        node.setId(taskParam.getTaskId());
        node = nodeService.queryNodeEntity(node);

        Schedule schedule = getSchedule(taskParam, node, null);
        atomScheduleService.uninstallOperators(schedule);

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * runtime 算子训练异常
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/runningException")
    public Integer runningException(@RequestBody RuntimeEntity runtimeEntity) {
        // 1、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAIN_EXCEPTION);
        Integer status = runtimeService.updateRuntimeEntity(runtimeEntity);
        if (status != 1) {
            return status;
        }

        // 2、调度schedule（释放资源、保存数据集）

        return 1;
    }

    /**
     * runtime 算子训练服务异常
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/serviceException")
    public Integer serviceException(@RequestBody RuntimeEntity runtimeEntity) {
        // 1、修改状态
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.SERVICE_EXCEPTION);
        Integer status = runtimeService.updateRuntimeEntity(runtimeEntity);
        if (status != 1) {
            return status;
        }

        // 2、调度schedule（释放资源、保存数据集）

        return 1;
    }

    /**
     * 获取Schedule和Runtime数据
     * @param taskParam
     * @param node
     * @param runtimeEntityList
     * @return
     * @throws NacosException
     */
    public Schedule getSchedule(TaskParam taskParam, NodeEntity node, List<RuntimeEntity> runtimeEntityList) throws NacosException {
        // 2、根据资源关系查出依赖表
        ResourceRelationEntity resourceRelationEntity = new ResourceRelationEntity();
        resourceRelationEntity.setRelationType(RelationType.RESOURCE_RELATION);
        resourceRelationEntity.setRelateType(ResourceType.NODE);
        resourceRelationEntity.setRelateId(node.getId());
        List<ResourceRelationEntity> resourceRelations = resourceRelationService.queryResourceRelationEntitys(resourceRelationEntity);

        // 3、封装Schedule数据和Runtime数据
        Schedule schedule = new Schedule();

        // 设置部署实例和调度的object
        Deploy deploy = new Deploy();
        schedule.setDeploy(deploy);
        List<AtomInstances> instancesList = new ArrayList<>();
        //todo 部署实例获取 使用随机端口
        deploy.setInstancesList(instancesList);

        // 获取部署实例
        List<String> serviceNames = new ArrayList<>();
        serviceNames.add("atom-runtime-python-service-standalone");
        for (String serviceName : serviceNames) {
            List<Instance> allInstances = namingService.getAllInstances(serviceName);
            for (Instance instance : allInstances) {
                AtomInstances atomInstances = new AtomInstances();
                atomInstances.setIp(instance.getIp());
                atomInstances.setPort(instance.getPort());
                instancesList.add(atomInstances);
            }
        }

        //todo 当没实例时，抛出异常

        CreateOperator operatorCreateTo = new CreateOperator();
        operatorCreateTo.setTaskId(taskParam.getTaskId());
        schedule.setObject(operatorCreateTo);

        Map<String, String> hardwareConfig = new HashMap<String, String>();
        schedule.setHardwareConfig(hardwareConfig);
        Map<String, String> limits = new HashMap<String, String>();
        schedule.setLimits(limits);
        schedule.setRunParameter(taskParam.getRunParameter());
        schedule.setEnvs(taskParam.getEnvs());

        Strategy strategy = new Strategy();
        Map<String, String> labelMap = new HashMap<>();
        schedule.setNodeId(node.getId());
        schedule.setNodeName(node.getNodeName());
        schedule.setStrategy(strategy);
        schedule.setLabels(labelMap);
        strategy.setScheduleStrategyType(ScheduleStrategyType.DEFAULT_LABEL);
        strategy.setTiming("0");
        //todo 策略的标签

        /**
         * 节点、算子和k8s的rumtime
         * 有多个部署实例时，创建多个runtime
         */
        List<RuntimeEntity> nodeRuntimeList = new ArrayList<>();
        List<RuntimeEntity> operatorRuntimeList = new ArrayList();
        RuntimeEntity kubernetesRuntime = null;
        if (Objects.nonNull(runtimeEntityList)) {
            for (AtomInstances instance: instancesList) {
                RuntimeEntity nodeRuntime = new RuntimeEntity();
                nodeRuntime.setSpaceId(node.getSpaceId());
                nodeRuntime.setNodeId(node.getId());
                nodeRuntime.setCaseSourceId(node.getId());
                nodeRuntime.setCaseSourceType(CaseSourceType.NODE);
                nodeRuntime.setServerIp(instance.getIp());
                nodeRuntime.setServerPort(instance.getPort());
                nodeRuntime.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
                nodeRuntime.setStartTime(new Date());
                nodeRuntime.setEstimateStartTime(new Date());
                //todo runtime的启动/关闭人需要用户管理模块的字段
                nodeRuntime.setStartId(1L);
                nodeRuntime.setStartName("");
                nodeRuntime.setEndId(1L);
                nodeRuntime.setEndName("");
                nodeRuntimeList.add(nodeRuntime);

                RuntimeEntity operatorRuntime = new RuntimeEntity();
                operatorRuntime.setSpaceId(node.getSpaceId());
                operatorRuntime.setNodeId(node.getId());
                operatorRuntime.setCaseSourceType(CaseSourceType.OPERATOR);
                operatorRuntime.setServerIp(instance.getIp());
                operatorRuntime.setServerPort(instance.getPort());
                operatorRuntime.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
                operatorRuntime.setStartTime(new Date());
                operatorRuntime.setEstimateStartTime(new Date());
                //todo runtime的启动/关闭人需要用户管理模块的字段
                operatorRuntime.setStartId(1L);
                operatorRuntime.setStartName("");
                operatorRuntime.setEndId(1L);
                operatorRuntime.setEndName("");
                operatorRuntimeList.add(operatorRuntime);
            }
        }

        for (ResourceRelationEntity resourceRelation : resourceRelations) {
            // 模型
            if (resourceRelation.getBeRelatedType() == ResourceType.MODEL) {
                ModelEntity model = new ModelEntity();
                model.setId(resourceRelation.getBeRelatedId());
                model = modelService.queryModelEntity(model);
                operatorCreateTo.setModelTo(model);
            }
            // 算子
            if (resourceRelation.getBeRelatedType() == ResourceType.OPERATOR) {
                OperatorEntity operator = new OperatorEntity();
                operator.setId(resourceRelation.getBeRelatedId());
                operator = operatorService.queryOperatorEntity(operator);
                for (RuntimeEntity operatorRuntime: operatorRuntimeList) {
                    operatorRuntime.setCaseSourceId(operator.getId());
                }
                operatorCreateTo.setOperatorTo(operator);
                schedule.setOperatorRuntimeType(operator.getOperatorRuntimeType());

                // 推理算子则需要创建k8s的runtime
                if (Objects.equals(operator.getOperatorRuntimeType(), ModelCreateType.REASON)) {
                    kubernetesRuntime = new RuntimeEntity();
                    kubernetesRuntime.setSpaceId(node.getSpaceId());
                    kubernetesRuntime.setCaseSourceType(CaseSourceType.JOB);

                    //todo 获取k8s部署实例的IP+PORT
                    kubernetesRuntime.setServerIp("-1");
                    kubernetesRuntime.setServerPort(-1);

                    kubernetesRuntime.setNodeId(node.getId());
                    kubernetesRuntime.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
                    kubernetesRuntime.setStartTime(new Date());
                    kubernetesRuntime.setEstimateStartTime(new Date());
                    //todo runtime的启动/关闭人需要用户管理模块的字段
                    kubernetesRuntime.setStartId(1L);
                    kubernetesRuntime.setStartName("");
                    kubernetesRuntime.setEndId(1L);
                    kubernetesRuntime.setEndName("");
                }
            }
            // 数据源
            if (resourceRelation.getBeRelatedType() == ResourceType.DATASOURCE) {
                DataSourceEntity dataSource = new DataSourceEntity();
                dataSource.setId(resourceRelation.getBeRelatedId());
                dataSource = dataSourceService.queryDataSourceEntity(dataSource);
                ConnectionEntity connection = new ConnectionEntity();
                connection.setId(dataSource.getConnectionId());
                connection = connectionService.queryConnectionEntity(connection);
                operatorCreateTo.setModelConnect(connection);

                SourceAndConnect sourceAndConnect = new SourceAndConnect();
                sourceAndConnect.setSourceTo(dataSource);
                sourceAndConnect.setConnectTo(connection);
                List<SourceAndConnect> sourceAndConnects = new ArrayList<>();
                sourceAndConnects.add(sourceAndConnect);
                operatorCreateTo.setSourceAndConnects(sourceAndConnects);

                for (RuntimeEntity nodeRuntime: nodeRuntimeList) {
                    nodeRuntime.setServiceInfoId(resourceRelation.getBeRelatedId());
                }
                for (RuntimeEntity operatorRuntime: operatorRuntimeList) {
                    operatorRuntime.setServiceInfoId(resourceRelation.getBeRelatedId());
                }
            }

            // todo operatorCreateTo设置的SourceAccountTo需要用户管理模块提供

            // 服务配置信息
            if (resourceRelation.getBeRelatedType() == ResourceType.SERVICE_INFO) {
                ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                hardwareConfig.put("cpu", String.valueOf(serviceInfo.getSiCpu()));
                hardwareConfig.put("gpu", String.valueOf(serviceInfo.getSiGpu()));
                hardwareConfig.put("memory", String.valueOf(serviceInfo.getSiDisplayMemory()));
                hardwareConfig.put("displayMemory", String.valueOf(serviceInfo.getSiDisplayMemory()));
                labelMap.put(serviceInfo.getSiName(), serviceInfo.getSiLabel());

                for (RuntimeEntity nodeRuntime: nodeRuntimeList) {
                    nodeRuntime.setLabel(serviceInfo.getSiLabel());
                }
                for (RuntimeEntity operatorRuntime: operatorRuntimeList) {
                    operatorRuntime.setLabel(serviceInfo.getSiLabel());
                }
            }
            // 服务最大配置信息
            if (resourceRelation.getBeRelatedType() == ResourceType.MAX_SERVICE_INFO) {
                ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                limits.put("max_service_config", JSON.toJSONString(serviceInfo));
            }
            // 服务最小配置信息
            if (resourceRelation.getBeRelatedType() == ResourceType.MIN_SERVICE_INFO) {
                ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                limits.put("min_service_config", JSON.toJSONString(serviceInfo));
            }
        }
        operatorCreateTo.getModelTo().setOperatorId(operatorCreateTo.getOperatorTo().getId());

        // 如果是创建训练任务，则设置runtime信息
        if (Objects.nonNull(runtimeEntityList)) {
            runtimeEntityList.addAll(nodeRuntimeList);
            runtimeEntityList.addAll(operatorRuntimeList);
            if (Objects.nonNull(kubernetesRuntime)) {
                runtimeEntityList.add(kubernetesRuntime);
            }
        }

        return schedule;
    }

    /**
     * 修改Runtime信息
     * @param runtimeEntityList
     */
    public void updateRuntimeStatus(List<RuntimeEntity> runtimeEntityList, Integer scheduleStatus) {
        // 调度成功
        if (200 == scheduleStatus) {
            for (RuntimeEntity runtimeEntity : runtimeEntityList) {
                if (Objects.equals(CaseSourceType.NODE, runtimeEntity.getCaseSourceType())) {
                    runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
                }
                if (Objects.equals(CaseSourceType.OPERATOR, runtimeEntity.getCaseSourceType())) {
                    runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
                }
                if (Objects.equals(CaseSourceType.JOB, runtimeEntity.getCaseSourceType())) {
                    runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
                }
            }
        } else {// 调度失败
            for (RuntimeEntity runtimeEntity : runtimeEntityList) {
                if (Objects.equals(CaseSourceType.NODE, runtimeEntity.getCaseSourceType())) {
                    runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAIN_EXCEPTION);
                }
                if (Objects.equals(CaseSourceType.OPERATOR, runtimeEntity.getCaseSourceType())) {
                    runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAIN_EXCEPTION);
                }
                if (Objects.equals(CaseSourceType.JOB, runtimeEntity.getCaseSourceType())) {
                    runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUE_CANCELING);
                }
            }
        }
    }

}
