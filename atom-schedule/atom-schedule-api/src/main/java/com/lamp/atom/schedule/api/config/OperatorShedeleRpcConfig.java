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
