package com.lamp.atom.schedule.api.config;

import lombok.Data;

/**
 * 
 * @author laohu
 *
 */
@Data
public class OperatorShedeleRpcConfig {

	private String isUseCloudNamespaceParsing;

	private String isUseEndpointParsingRule;

	private String endpoint;

	private String endpointPort;

	private String namespace;

	private String username;

	private String password;

	private String accessKey;

	private String secretKey;

	private String ramRoleName;

	private String serverAddr;

	private String contextPath;

	private String clusterName;

	private String encode;

	private String configLongPollTimeout;

	private String configRetryTime;

	private String maxRetry;

	private String enableRemoteSyncConfig;

	private String namingLoadCacheAtStart;

	private String namingCacheRegistryDir;

	private String namingClientBeatThreadCount;

	private String namingPollingThreadCount;

	private String namingRequestDomainMaxRetryCount;

	private String namingPushEmptyProtection;

}
