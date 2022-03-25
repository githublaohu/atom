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
public class OperatorScheduleRpcConfig {

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

	public String getIsUseCloudNamespaceParsing() {
		return isUseCloudNamespaceParsing;
	}

	public void setIsUseCloudNamespaceParsing(String isUseCloudNamespaceParsing) {
		this.isUseCloudNamespaceParsing = isUseCloudNamespaceParsing;
	}

	public String getIsUseEndpointParsingRule() {
		return isUseEndpointParsingRule;
	}

	public void setIsUseEndpointParsingRule(String isUseEndpointParsingRule) {
		this.isUseEndpointParsingRule = isUseEndpointParsingRule;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getEndpointPort() {
		return endpointPort;
	}

	public void setEndpointPort(String endpointPort) {
		this.endpointPort = endpointPort;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRamRoleName() {
		return ramRoleName;
	}

	public void setRamRoleName(String ramRoleName) {
		this.ramRoleName = ramRoleName;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getConfigLongPollTimeout() {
		return configLongPollTimeout;
	}

	public void setConfigLongPollTimeout(String configLongPollTimeout) {
		this.configLongPollTimeout = configLongPollTimeout;
	}

	public String getConfigRetryTime() {
		return configRetryTime;
	}

	public void setConfigRetryTime(String configRetryTime) {
		this.configRetryTime = configRetryTime;
	}

	public String getMaxRetry() {
		return maxRetry;
	}

	public void setMaxRetry(String maxRetry) {
		this.maxRetry = maxRetry;
	}

	public String getEnableRemoteSyncConfig() {
		return enableRemoteSyncConfig;
	}

	public void setEnableRemoteSyncConfig(String enableRemoteSyncConfig) {
		this.enableRemoteSyncConfig = enableRemoteSyncConfig;
	}

	public String getNamingLoadCacheAtStart() {
		return namingLoadCacheAtStart;
	}

	public void setNamingLoadCacheAtStart(String namingLoadCacheAtStart) {
		this.namingLoadCacheAtStart = namingLoadCacheAtStart;
	}

	public String getNamingCacheRegistryDir() {
		return namingCacheRegistryDir;
	}

	public void setNamingCacheRegistryDir(String namingCacheRegistryDir) {
		this.namingCacheRegistryDir = namingCacheRegistryDir;
	}

	public String getNamingClientBeatThreadCount() {
		return namingClientBeatThreadCount;
	}

	public void setNamingClientBeatThreadCount(String namingClientBeatThreadCount) {
		this.namingClientBeatThreadCount = namingClientBeatThreadCount;
	}

	public String getNamingPollingThreadCount() {
		return namingPollingThreadCount;
	}

	public void setNamingPollingThreadCount(String namingPollingThreadCount) {
		this.namingPollingThreadCount = namingPollingThreadCount;
	}

	public String getNamingRequestDomainMaxRetryCount() {
		return namingRequestDomainMaxRetryCount;
	}

	public void setNamingRequestDomainMaxRetryCount(String namingRequestDomainMaxRetryCount) {
		this.namingRequestDomainMaxRetryCount = namingRequestDomainMaxRetryCount;
	}

	public String getNamingPushEmptyProtection() {
		return namingPushEmptyProtection;
	}

	public void setNamingPushEmptyProtection(String namingPushEmptyProtection) {
		this.namingPushEmptyProtection = namingPushEmptyProtection;
	}
}
