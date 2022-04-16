package com.lamp.atom.service.operator.consumers.function;

import static java.util.Collections.emptyMap;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.lamp.atom.schedule.api.config.OperatorScheduleConfig;
import com.lamp.atom.schedule.core.AtomScheduleService;

import lombok.Setter;

/**
 * 1. 从本地读取配置 2. 从nacos读取配置 3.
 * 
 * @author laohu
 *
 */
@Component
@ConfigurationProperties(prefix="atom.schedule")
public class ScheduleSuper implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Setter
	private OperatorScheduleConfig operatorScheduleConfig;

	private AtomScheduleService atomScheduleService;

	@Autowired
	private ApplicationContext appContext;

	@PostConstruct
	private void init() throws Exception {
		Properties properties = this.getProperties();

		if (properties.isEmpty()) {
			// 如果是null，本地文件
			String config = this.getKubernetesConfigByFile();
			this.operatorScheduleConfig.getOperatorScheduleKubernetesConfig().setConfigYaml(config);
		} else {
			// 如果不是null，是nacos
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(this.operatorScheduleConfig.getOperatorScheduleRpcConfig());
			Properties nacosConfig = new Properties();
			nacosConfig.putAll(jsonObject);
			ConfigService configService = ConfigFactory.createConfigService(properties);
			String config = configService.getConfig(this.operatorScheduleConfig.getOperatorScheduleKubernetesConfig().getConfigName(),
					this.operatorScheduleConfig.getOperatorScheduleRpcConfig().getNamespace(), 3000);
			this.operatorScheduleConfig.getOperatorScheduleKubernetesConfig().setConfigYaml(config);
		}
		atomScheduleService = new AtomScheduleService(this.operatorScheduleConfig);
	}
	
	@Bean
	public AtomScheduleService getAtomScheduleService() {
		return atomScheduleService;
	}

	private String getKubernetesConfigByFile() throws IOException {
		Resource resource = new ClassPathResource(operatorScheduleConfig.getOperatorScheduleKubernetesConfig().getConfigName());
		if (resource.exists()) {
			return FileUtils.readFileToString(resource.getFile(), CharEncoding.UTF_8);
		}
		return null;
	}

	private Properties getProperties() {
		Properties properties = new Properties();
		// If Bean is absent , source will be empty.
		Map<?, ?> propertiesSource = beanFactory.containsBean("GLOBAL_NACOS_PROPERTIES_BEAN_NAME")
				? beanFactory.getBean("GLOBAL_NACOS_PROPERTIES_BEAN_NAME", Properties.class)
				: emptyMap();
		properties.putAll(propertiesSource);
		return properties;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

}
