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
import com.lamp.atom.schedule.api.deploy.AtomInstances;
import com.lamp.atom.schedule.api.deploy.Deploy;
import com.lamp.atom.schedule.api.strategy.ScheduleStrategyType;
import com.lamp.atom.schedule.api.strategy.Strategy;
import com.lamp.atom.schedule.core.AtomScheduleService;
import com.lamp.atom.schedule.python.operator.CreateOperator;
import com.lamp.atom.service.domain.CaseSourceType;
import com.lamp.atom.service.domain.DeployType;
import com.lamp.atom.service.domain.ModelCreateType;
import com.lamp.atom.service.domain.NodeStatus;
import com.lamp.atom.service.domain.OperatorRuntimeStatus;
import com.lamp.atom.service.domain.OperatorRuntimeType;
import com.lamp.atom.service.domain.RelationType;
import com.lamp.atom.service.domain.ResourceType;
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
@Api(tags = {"????????????????????????"})
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
     * ??????nacos??????
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
     * console?????????????????????
     *
     * @param taskParam ??????????????????
     * @return
     */
    @PostMapping("/startExamTask")
    public ResultObject<String> startExamTask(@RequestBody TaskParam taskParam) {
        OrganizationEntity organization = organizationService.queryOrganizationById(taskParam.getTaskId());
        // 1????????????????????????????????????ID??????????????????
        ResourceRelationEntity nodeRelationEntity = new ResourceRelationEntity();
        nodeRelationEntity.setRelationType(RelationType.NODE_RELATION);
        nodeRelationEntity.setBeRelatedId(organization.getId());
        nodeRelationEntity.setBeRelatedType(ResourceType.ORGANIZATION);
        List<ResourceRelationEntity> nodeRelations = resourceRelationService.queryResourceRelationEntitys(nodeRelationEntity);

        // 2????????????????????????????????????
        List<Schedule> scheduleList = new ArrayList<>();
        List<RuntimeEntity> runtimeList = new ArrayList<>();
        ResourceRelationEntity resourceRelationEntity = new ResourceRelationEntity();
        resourceRelationEntity.setRelationType(RelationType.RESOURCE_RELATION);
        resourceRelationEntity.setRelateType(ResourceType.NODE);

        // ??????Node
        for (ResourceRelationEntity nodeRelation : nodeRelations) {
            NodeEntity node = new NodeEntity();
            node.setId(nodeRelation.getRelateId());
            node = nodeService.queryNodeEntity(node);

            // ?????????????????????????????????????????????
            if (node.getNodeStatus() != NodeStatus.EDIT_FINISH) {
                log.info("???????????????????????? {}", node);
                return ResultObjectEnums.NODE_STATUS_CHECK_FAIL.getResultObject();
            }

            resourceRelationEntity.setRelateId(node.getId());
            List<ResourceRelationEntity> resourceRelations = resourceRelationService.queryResourceRelationEntitys(resourceRelationEntity);

            // 3?????????Schedule?????????Runtime??????
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
            //todo ???????????????

            RuntimeEntity runtime = new RuntimeEntity();
            runtime.setSpaceId(node.getSpaceId());
            runtime.setCaseSourceType(CaseSourceType.NODE);
            runtime.setNodeId(node.getId());
            runtime.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
            runtime.setStartTime(new Date());
            runtime.setEstimateStartTime(new Date());
            //todo runtime?????????/??????????????????????????????????????????

            for (ResourceRelationEntity resourceRelation : resourceRelations) {
                //??????????????????
                if (resourceRelation.getBeRelatedType() == ResourceType.SERVICE_INFO) {
                    ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                    hardwareConfig.put("cpu", String.valueOf(serviceInfo.getSiCpu()));
                    hardwareConfig.put("gpu", String.valueOf(serviceInfo.getSiGpu()));
                    hardwareConfig.put("memory", String.valueOf(serviceInfo.getSiDisplayMemory()));
                    hardwareConfig.put("displayMemory", String.valueOf(serviceInfo.getSiDisplayMemory()));
                    labelMap.put(serviceInfo.getSiName(), serviceInfo.getSiLabel());

                    runtime.setLabel(serviceInfo.getSiLabel());
                }
                //????????????????????????
                if (resourceRelation.getBeRelatedType() == ResourceType.MAX_SERVICE_INFO) {
                    ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                    limits.put("max_service_config", JSON.toJSONString(serviceInfo));
                }
                //????????????????????????
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

        // 4?????????Runtime?????????????????????
        runtimeService.batchInsertRuntimeEntity(runtimeList);
        for (Schedule schedule : scheduleList) {
            atomScheduleService.createOperators(schedule);
        }

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * console?????????????????????
     *
     * @param taskParam ??????????????????
     * @return
     */
    @PostMapping("/stopExamTask")
    public ResultObject<String> stopExamTask(@RequestBody TaskParam taskParam) {
        OrganizationEntity organization = organizationService.queryOrganizationById(taskParam.getTaskId());
        // 1????????????????????????????????????ID??????????????????
        ResourceRelationEntity nodeRelationEntity = new ResourceRelationEntity();
        nodeRelationEntity.setRelationType(RelationType.NODE_RELATION);
        nodeRelationEntity.setBeRelatedId(organization.getId());
        nodeRelationEntity.setBeRelatedType(ResourceType.ORGANIZATION);
        List<ResourceRelationEntity> nodeRelations = resourceRelationService.queryResourceRelationEntitys(nodeRelationEntity);

        // 2????????????????????????????????????
        List<Schedule> scheduleList = new ArrayList<>();
        List<RuntimeEntity> runtimeList = new ArrayList<>();
        ResourceRelationEntity resourceRelationEntity = new ResourceRelationEntity();
        resourceRelationEntity.setRelationType(RelationType.RESOURCE_RELATION);
        resourceRelationEntity.setRelateType(ResourceType.NODE);

        // 3?????????Schedule?????????Runtime??????
        for (ResourceRelationEntity nodeRelation : nodeRelations) {
            Schedule schedule = new Schedule();
            schedule.setNodeId(nodeRelation.getRelateId());
            scheduleList.add(schedule);

            RuntimeEntity runtimeEntity = new RuntimeEntity();
            runtimeEntity.setNodeId(nodeRelation.getRelateId());
            List<RuntimeEntity> runtimeEntityList = runtimeService.queryRuntimeEntitys(runtimeEntity);
            runtimeList.addAll(runtimeEntityList);
        }

        // 4?????????Runtime?????????????????????
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
     * console?????????????????????
     *
     * @param taskParam ??????????????????
     * @return
     */
    @PostMapping("/startNodeTask")
    @ApiOperation(value = "??????????????????")
    public ResultObject<String> startNodeTask(@RequestBody TaskParam taskParam) throws NacosException {
        // 0???????????????
        if (Objects.isNull(taskParam.getTaskId()) ||
                Objects.isNull(taskParam.getOperatorRuntimeType())) {
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }

        // 1?????????????????????????????????????????????????????????????????????
        NodeEntity node = new NodeEntity();
        node.setId(taskParam.getTaskId());
        node = nodeService.queryNodeEntity(node);

        if (node.getNodeStatus() != NodeStatus.EDIT_FINISH) {
            log.info("???????????????????????? {}", node);
            return ResultObjectEnums.NODE_STATUS_CHECK_FAIL.getResultObject();
        }

        // 3????????????????????????Runtime??????
        List<RuntimeEntity> runtimeEntityList = new ArrayList<>();
        Schedule schedule = getSchedule(taskParam, node, runtimeEntityList);

        runtimeService.batchInsertRuntimeEntity(runtimeEntityList);
        // 4?????????Runtime?????????????????????
        atomScheduleService.createOperators(schedule);

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * console??????????????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/editing")
    public Integer editStart(@RequestBody RuntimeEntity runtimeEntity) {
        //1???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * console?????????????????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/editCancel")
    public Integer editCancel(@RequestBody RuntimeEntity runtimeEntity) {
        //1???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDIT_CANCEL);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * console?????????
     */
    @PostMapping("/queuing")
    public Integer queuing(@RequestBody RuntimeEntity runtimeEntity) {
        // 1?????????schedule

        // 2???????????????
        //runtimeEntity.setOperatorStatus(RuntimeStatus.QUEUING);
        this.atomScheduleService.createOperators(null);
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * console??????????????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/queueCanceling")
    public Integer queueCanceling(@RequestBody RuntimeEntity runtimeEntity) {
        // 1?????????schedule

        // 2???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUE_CANCELING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * schedule ??????????????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/queueCancelFinish")
    public Integer queueCancelFinish(@RequestBody RuntimeEntity runtimeEntity) {
        // 1???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUE_CANCEL);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * console ?????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/occupying")
    public Integer occupying(@RequestBody RuntimeEntity runtimeEntity) {
        // 1?????????schedule

        // 2???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

//    /**
//     * ???????????????runtime ?????????????????????
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/caseCreating")
//    public Integer caseCreating(@RequestBody OperatorEntity operatorEntity) {
//        //1???????????????
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.CASE_CREATING);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * ???????????????runtime ????????????????????????
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/caseCreateFinish")
//    public Integer caseCreateFinish(@RequestBody OperatorEntity operatorEntity) {
//        //1???????????????
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.CASE_CREATE_FINISH);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * ???????????????runtime ???????????????
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/dataUploading")
//    public Integer dataUploading(@RequestBody OperatorEntity operatorEntity) {
//        //1???????????????
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.DATA_UPLOADING);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }
//
//    /**
//     * ???????????????runtime ??????????????????
//     * @param operatorEntity
//     * @return
//     */
//    @PostMapping("/dataUploadFinish")
//    public Integer dataUploadFinish(@RequestBody OperatorEntity operatorEntity) {
//        //1???????????????
//        operatorEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.DATA_UPLOAD_FINISH);
//        return operatorService.updateOperatorEntity(operatorEntity);
//    }

    /**
     * runtime ??????????????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/running")
    public Integer running(@RequestBody RuntimeEntity runtimeEntity) {
        //1???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAINING);
        Integer status = runtimeService.updateRuntimeEntity(runtimeEntity);
        if (status != 1) {
            return status;
        }
        return 1;
    }

    /**
     * runtime ????????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/testing")
    public Integer testing(@RequestBody RuntimeEntity runtimeEntity) {
        //1???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TESTING);
        return runtimeService.updateRuntimeEntity(runtimeEntity);
    }

    /**
     * runtime ????????????????????????
     *
     * ???????????????????????????
     * ???????????????ID?????????????????????
     *
     * @param taskParam
     * @return
     */
    @PostMapping("/runningAutoFinish")
    public ResultObject<String> runningAutoFinish(@RequestBody TaskParam taskParam) throws NacosException {
        // 0???????????????
        if (Objects.isNull(taskParam.getTaskId()) ||
                Objects.isNull(taskParam.getOperatorRuntimeType())) {
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }

        // 1????????????????????????????????????ID?????????????????????????????????runtime)
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.setNodeId(taskParam.getTaskId());
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.AUTO_FINISH);
        Integer status = runtimeService.updateRuntimeStatus(runtimeEntity);
        if (status < 1) {
            return ResultObjectEnums.FAIL.getResultObject();
        }

        // 2?????????schedule????????????????????????????????????
        NodeEntity node = new NodeEntity();
        node.setId(taskParam.getTaskId());
        node = nodeService.queryNodeEntity(node);

        Schedule schedule = getSchedule(taskParam, node, null);
        atomScheduleService.uninstallOperators(schedule);

        // 3???????????????????????????????????????????????????????????????
        if (OperatorRuntimeType.TRAIN == taskParam.getOperatorRuntimeType()) {
            // ???????????????????????????????????????
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

            //??????????????????????????????????????????????????????????????????????????????
            if (Objects.equals(operator.getOperatorRuntimeType(), OperatorRuntimeType.REASONING)) {
                DeployType deployType = operator.getDeployType();
                switch (deployType) {
                    case AUTO_DEPLOY:
                        //????????????????????????????????????
                        TaskParam reasonTask = new TaskParam();
                        reasonTask.setTaskId(node.getId());
                        reasonTask.setOperatorRuntimeType(OperatorRuntimeType.REASONING);
                        this.startNodeTask(reasonTask);
                        break;
                    case GREY_DEPLOY:
                        //????????????
                        break;
                    case TOUCH_DEPLOY:
                        //????????????
                        break;
                    case NOT_DEPLOY:
                        //?????????
                        break;
                }
            }
        }

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * console ????????????????????????
     *
     * @param taskParam
     * @return
     */
    @PostMapping("/runningFinish")
    @ApiOperation(value = "????????????????????????")
    public ResultObject<String> runningFinish(@RequestBody TaskParam taskParam) throws NacosException {
        //0???????????????
        if (Objects.isNull(taskParam.getTaskId()) ||
                Objects.isNull(taskParam.getOperatorRuntimeType())) {
            return ResultObjectEnums.FAIL.CHECK_PARAMETERS_FAIL.getResultObject();
        }

        // 1?????????????????????????????????????????????ID?????????????????????????????????runtime)
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.setNodeId(taskParam.getTaskId());
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.AUTO_FINISH);
        Integer status = runtimeService.updateRuntimeStatus(runtimeEntity);
        if (status < 1) {
            return ResultObjectEnums.FAIL.getResultObject();
        }

        // 2?????????schedule????????????????????????????????????
        NodeEntity node = new NodeEntity();
        node.setId(taskParam.getTaskId());
        node = nodeService.queryNodeEntity(node);

        Schedule schedule = getSchedule(taskParam, node, null);
        atomScheduleService.uninstallOperators(schedule);

        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * runtime ??????????????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/runningException")
    public Integer runningException(@RequestBody RuntimeEntity runtimeEntity) {
        // 1???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.TRAIN_EXCEPTION);
        Integer status = runtimeService.updateRuntimeEntity(runtimeEntity);
        if (status != 1) {
            return status;
        }

        // 2?????????schedule????????????????????????????????????

        return 1;
    }

    /**
     * runtime ????????????????????????
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/serviceException")
    public Integer serviceException(@RequestBody RuntimeEntity runtimeEntity) {
        // 1???????????????
        runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.SERVICE_EXCEPTION);
        Integer status = runtimeService.updateRuntimeEntity(runtimeEntity);
        if (status != 1) {
            return status;
        }

        // 2?????????schedule????????????????????????????????????

        return 1;
    }

	public Schedule getSchedule(TaskParam taskParam, NodeEntity node, List<RuntimeEntity> runtimeEntityList) throws NacosException {
        // 2????????????????????????????????????
        ResourceRelationEntity resourceRelationEntity = new ResourceRelationEntity();
        resourceRelationEntity.setRelationType(RelationType.RESOURCE_RELATION);
        resourceRelationEntity.setRelateType(ResourceType.NODE);
        resourceRelationEntity.setRelateId(node.getId());
        List<ResourceRelationEntity> resourceRelations = resourceRelationService.queryResourceRelationEntitys(resourceRelationEntity);

        // 3?????????Schedule?????????Runtime??????
        Schedule schedule = new Schedule();

        // ??????????????????????????????object
        Deploy deploy = new Deploy();
        schedule.setDeploy(deploy);
        List<AtomInstances> instancesList = new ArrayList<>();
        deploy.setInstancesList(instancesList);

        // ??????runtime????????????
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
        //todo ???????????????

        // ??????????????????k8s???rumtime
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
            //todo runtime?????????/??????????????????????????????????????????
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
            //todo runtime?????????/??????????????????????????????????????????
            operatorRuntime.setStartId(1L);
            operatorRuntime.setStartName("");
            operatorRuntime.setEndId(1L);
            operatorRuntime.setEndName("");

        }

        for (ResourceRelationEntity resourceRelation : resourceRelations) {
            // ??????
            if (resourceRelation.getBeRelatedType() == ResourceType.MODEL) {
                ModelEntity model = new ModelEntity();
                model.setId(resourceRelation.getBeRelatedId());
                model = modelService.queryModelEntity(model);
                operatorCreateTo.setModelTo(model);
            }
            // ??????
            if (resourceRelation.getBeRelatedType() == ResourceType.OPERATOR) {
                OperatorEntity operator = new OperatorEntity();
                operator.setId(resourceRelation.getBeRelatedId());
                operator = operatorService.queryOperatorEntity(operator);
                operatorCreateTo.setOperatorTo(operator);
                schedule.setOperatorRuntimeType(operator.getOperatorRuntimeType());

                // ???????????????????????????k8s???runtime
                if (!ModelCreateType.REASON.equals(operator.getOperatorRuntimeType())) {
                    kubernetesRuntime = new RuntimeEntity();
                    kubernetesRuntime.setSpaceId(node.getSpaceId());
                    kubernetesRuntime.setCaseSourceType(CaseSourceType.JOB);
                    kubernetesRuntime.setNodeId(node.getId());
                    kubernetesRuntime.setOperatorRuntimeStatus(OperatorRuntimeStatus.EDITING);
                    kubernetesRuntime.setStartTime(new Date());
                    kubernetesRuntime.setEstimateStartTime(new Date());
                    //todo runtime?????????/??????????????????????????????????????????
                    kubernetesRuntime.setStartId(1L);
                    kubernetesRuntime.setStartName("");
                    kubernetesRuntime.setEndId(1L);
                    kubernetesRuntime.setEndName("");
                }
            }
            // ?????????
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

            // todo operatorCreateTo?????????SourceAccountTo??????????????????????????????

            // ??????????????????
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
            // ????????????????????????
            if (resourceRelation.getBeRelatedType() == ResourceType.MAX_SERVICE_INFO) {
                ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                limits.put("max_service_config", JSON.toJSONString(serviceInfo));
            }
            // ????????????????????????
            if (resourceRelation.getBeRelatedType() == ResourceType.MIN_SERVICE_INFO) {
                ServiceInfoEntity serviceInfo = serviceInfoService.queryServiceInfoEntityById(resourceRelation.getBeRelatedId());
                limits.put("min_service_config", JSON.toJSONString(serviceInfo));
            }
        }

        // ?????????????????????????????????runtime??????
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
