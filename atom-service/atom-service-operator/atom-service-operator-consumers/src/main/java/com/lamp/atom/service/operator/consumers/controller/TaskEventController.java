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

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.deploy.AtomInstances;
import com.lamp.atom.schedule.api.deploy.Deploy;
import com.lamp.atom.schedule.api.strategy.ScheduleStrategyType;
import com.lamp.atom.schedule.api.strategy.Strategy;
import com.lamp.atom.schedule.python.operator.CreateOperator;
import com.lamp.atom.service.domain.*;
import com.lamp.atom.service.operator.consumers.function.PortCreatingFunction;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.atom.service.operator.domain.SourceAndConnect;
import com.lamp.atom.service.operator.domain.TaskParam;
import com.lamp.atom.service.operator.entity.*;
import com.lamp.atom.service.operator.service.*;
import com.lamp.decoration.core.result.ResultObject;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.lamp.atom.schedule.core.AtomScheduleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.*;

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
    private PortCreatingFunction portCreatingFunction;
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
                    runtime.setSourceId(resourceRelation.getBeRelatedId());
                }
            }

            scheduleList.add(schedule);
            runtimeList.add(runtime);
        }

        // 4、保存Runtime信息，开启调度
        runtimeService.batchInsertRuntimeEntity(runtimeList);
        for (Schedule schedule : scheduleList) {
            atomScheduleService.createOperators(schedule);
        }

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

        // 4、修改Runtime信息，关闭调度
        for (RuntimeEntity runtimeEntity : runtimeList) {
            runtimeEntity.setEndTime(new Date());
            runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.MANUAL_FINISH);
        }
        runtimeService.batchUpdateRuntimeEntity(runtimeList);
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
        // 0、字段判空
        if (Objects.isNull(taskParam.getTaskId()) ||
                Objects.isNull(taskParam.getOperatorRuntimeType())) {
            return ResultObjectEnums.FAIL.CHECK_PARAMETERS_FAIL.getResultObject();
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
        List<RuntimeEntity> runtimeEntityList = new ArrayList<>();
        Schedule schedule = getSchedule(taskParam, node, runtimeEntityList);

        // 4、保存Runtime信息，开启调度
        atomScheduleService.createOperators(schedule);
        runtimeService.batchInsertRuntimeEntity(runtimeEntityList);

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
            return ResultObjectEnums.FAIL.CHECK_PARAMETERS_FAIL.getResultObject();
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
        if (Objects.equals(taskParam.getOperatorRuntimeType() , ModelCreateType.TRAIN)) {
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
        deploy.setInstancesList(instancesList);

        // 获取runtime部署实例
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

        CreateOperator operatorCreateTo = new CreateOperator();
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

        // 节点、算子和k8s的rumtime
        RuntimeEntity nodeRuntime = new RuntimeEntity();
        RuntimeEntity operatorRuntime = new RuntimeEntity();
        RuntimeEntity kubernetesRuntime = null;
        if (Objects.nonNull(runtimeEntityList)) {
            nodeRuntime.setSpaceId(node.getSpaceId());
            nodeRuntime.setCaseSourceType(CaseSourceType.NODE);
            nodeRuntime.setNodeId(node.getId());
            nodeRuntime.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
            nodeRuntime.setStartTime(new Date());
            nodeRuntime.setEstimateStartTime(new Date());
            //todo runtime的启动/关闭人需要用户管理模块的字段
            nodeRuntime.setStartId(1L);
            nodeRuntime.setStartName("");
            nodeRuntime.setEndId(1L);
            nodeRuntime.setEndName("");

            operatorRuntime.setSpaceId(node.getSpaceId());
            operatorRuntime.setCaseSourceType(CaseSourceType.OPERATOR);
            operatorRuntime.setNodeId(node.getId());
            operatorRuntime.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
            operatorRuntime.setStartTime(new Date());
            operatorRuntime.setEstimateStartTime(new Date());
            //todo runtime的启动/关闭人需要用户管理模块的字段
            operatorRuntime.setStartId(1L);
            operatorRuntime.setStartName("");
            operatorRuntime.setEndId(1L);
            operatorRuntime.setEndName("");

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
                operatorCreateTo.setOperatorTo(operator);
                schedule.setOperatorRuntimeType(operator.getOperatorRuntimeType());

                // 推理算子则需要创建k8s的runtime
                if (!Objects.equals(operator.getOperatorRuntimeType(), ModelCreateType.REASON)) {
                    kubernetesRuntime = new RuntimeEntity();
                    kubernetesRuntime.setSpaceId(node.getSpaceId());
                    kubernetesRuntime.setCaseSourceType(CaseSourceType.JOB);
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

                nodeRuntime.setSourceId(resourceRelation.getBeRelatedId());
                operatorRuntime.setSourceId(resourceRelation.getBeRelatedId());
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

                nodeRuntime.setLabel(serviceInfo.getSiLabel());
                operatorRuntime.setLabel(serviceInfo.getSiLabel());
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

        // 如果是创建调度，则设置runtime信息
        if (Objects.nonNull(runtimeEntityList)) {
            runtimeEntityList.add(nodeRuntime);
            runtimeEntityList.add(operatorRuntime);
            if (Objects.nonNull(kubernetesRuntime)) {
                runtimeEntityList.add(kubernetesRuntime);
            }
        }

        return schedule;
    }

}
