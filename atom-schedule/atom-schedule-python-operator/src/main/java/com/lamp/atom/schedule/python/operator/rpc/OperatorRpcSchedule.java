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
package com.lamp.atom.schedule.python.operator.rpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lamp.atom.schedule.api.AtomOperatorShedule;
import com.lamp.atom.schedule.api.Shedule;
import com.lamp.atom.schedule.api.config.OperatorShedeleRpcConfig;
import com.lamp.atom.schedule.api.deploy.AtomInstances;
import com.lamp.atom.schedule.api.deploy.Deploy;
import com.lamp.atom.schedule.api.strategy.SheduleStrategyType;
import com.lamp.atom.schedule.python.operator.AtomOperatorRPCServier;
import com.lamp.atom.schedule.python.operator.CreateOperator;
import com.lamp.light.Light;

import lombok.extern.slf4j.Slf4j;

/**
 * 只负责推理
 * 
 * @author laohu
 *
 */
@Slf4j
public class OperatorRpcSchedule implements AtomOperatorShedule {

	private static String ATOM_RUNTIME_PYTHON_SERVICE_NAME = "atom-runtime-python-service-standalone";

	private static String ATOM_RUNTIME_OPERATOR_NAME = "atom-runtime-operator-";

	private NamingService namingService;

	private OperatorShedeleRpcConfig operatorShedeleRpcConfig;

	/**
	 * 重试是否需要
	 */
	@SuppressWarnings("unused")
	private ScheduledThreadPoolExecutor retryExecutor = new ScheduledThreadPoolExecutor(4);

	private Map<Instance, AtomOperatorRPCServier> runtimeClient = new ConcurrentHashMap<>();
	
	public OperatorRpcSchedule(OperatorShedeleRpcConfig operatorShedeleRpcConfig) throws NacosException {
		this.operatorShedeleRpcConfig = operatorShedeleRpcConfig;
		Properties properties = new Properties();
		JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(this.operatorShedeleRpcConfig));
		properties.putAll(jsonObject);
		namingService = NamingFactory.createNamingService(properties);
	}

	private AtomOperatorRPCServier createClient(Instance instance) throws Exception {
		AtomOperatorRPCServier atomOperatorRPCServier = runtimeClient.get(instance);
		if (Objects.isNull(atomOperatorRPCServier)) {
			Light light = Light.Builder().host(instance.getIp()).port(instance.getPort()).build();
			atomOperatorRPCServier = light.create(AtomOperatorRPCServier.class);
			runtimeClient.put(instance, atomOperatorRPCServier);
		}
		return atomOperatorRPCServier;

	}

	private List<Instance> getInstance(Shedule shedule) throws NacosException {
		List<Instance> instanceList = null;
		Deploy deploy = shedule.getDeploy();
		// 指定部署实例
		if (Objects.nonNull(deploy) && Objects.nonNull(deploy.getInstancesList())) {
			instanceList = new ArrayList<Instance>(deploy.getInstancesList().size());

			for (AtomInstances atomInstances : deploy.getInstancesList()) {
				Instance instance = new Instance();
				instance.setIp(atomInstances.getIp());
				instance.setPort(atomInstances.getPort());
				instanceList.add(instance);
			}
		} else {
			SheduleStrategyType sheduleStrategyType = shedule.getStrategy().getSheduleStrategyType();
			if (Objects.equals(sheduleStrategyType, SheduleStrategyType.DEFAULT_REPLACE)) {
				instanceList = namingService.getAllInstances(ATOM_RUNTIME_OPERATOR_NAME + shedule.getNoteName());
			} else if (Objects.equals(sheduleStrategyType, SheduleStrategyType.DEFAULT_TOTAL_QUANTITY)) {
				instanceList = namingService.getAllInstances(ATOM_RUNTIME_PYTHON_SERVICE_NAME);
			} else if (Objects.equals(sheduleStrategyType, SheduleStrategyType.DEFAULT_LABEL)) {
				instanceList = namingService.getAllInstances(ATOM_RUNTIME_PYTHON_SERVICE_NAME, "");
			} else if (Objects.equals(sheduleStrategyType, SheduleStrategyType.ATOM_RPC_RONDOM)) {
				instanceList = namingService.getAllInstances(ATOM_RUNTIME_PYTHON_SERVICE_NAME);
			}
		}
		return instanceList;
	}

	/**
	 * 全量 标签 覆盖
	 */
	@Override
	public void createOperators(Shedule shedule) {
		try {

			List<Instance> instanceList = this.getInstance(shedule);
			// 创建对象
			CreateOperator object = (CreateOperator) shedule.getObject();
			for (Instance instance : instanceList) {
				this.createClient(instance)
					.createOperators(object);
			}
			// 发送请求

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public void startOperators(Shedule shedule) {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspendOperators(Shedule shedule) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uninstallPperators(Shedule shedule) {
		// TODO Auto-generated method stub

	}

}
