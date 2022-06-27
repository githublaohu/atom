# Atom部署文档

## 一、环境准备

| 组件   | 版本  | 是否必须 |
| ------ | ----- | -------- |
| JDK    | 1.8   | 是       |
| Python | 3.7   | 是       |
| MySQL  | 8.0   | 是       |
| Nacos  | 2.0.3 | 是       |

## 二、启动算子服务

Nacos作为算子服务的**注册中心和配置中心**

算子服务的**Provider（生产者）和Consumer（消费者）**都是通过配置信息Nacos进行服务的配置，且将**服务注册**到Nacos

命名空间指定为atom-dev，具体见application.yml中的配置

#### 1、在Nacos对应的namespace中添加如下两个配置文件

###### 生产者服务配置

文件名：operator-provider-dev

```yaml
spring:
  datasource:
    name: lamp-atom
    url: jdbc:mysql://{mysql_ip}:{port}/atom?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull&allowPublicKeyRetrieval=true
    username: {username}
    password: {password}
    # using druid data source
    type: com.alibaba.druid.pool.DruidDataSource
    maxActive: 20
    initialSize: 1
    maxWait: 60000
    minIdle: 1
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: select 'x'
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    maxOpenPreparedStatements: 20
    # encrypt password
    filters: config,stat,slf4j

dubbo:
  scan:
    base-packages: com.lamp.atom.service.operator.provider.service.impl
  application:
    name: lamp-atom-service-operator
    owner: dome
    logger: slf4j
  protocol:
    name: dubbo
    port: 20880
    accesslog: true
  provider:
    timeout: 30000
    retries: 0
  registry:
    address: nacos://{nacos_ip}:{port}
    parmeters:
      namespace: atom-dev
    register: true
    subscribe: false

mybatis:
  checkConfig-location : false
  configuration:
    useGeneratedKeys: true
    mapUnderscoreToCamelCase: true
    name: atom-service-operator-provider

pagehelper:
  helperDialect: mysql
  reasonable: true
  supportMethodsArguments: true
  params: count=countSql
```

###### 消费者服务配置

文件名：operator-consumer-dev

```yaml
spring:
  application:
    name: lamp-atom-service-operator
    owner: dome
    logger: slf4j
    name: atom-service-operator-consumer

dubbo:
  protocol:
    name: dubbo
    port: 20880
    accesslog: true
  consume:
    timeout: 30000
    retries: 0
  registry:
    address: nacos://{nacos_ip}:{port}
    parmeters:
      namespace: atom-dev
    register: true
    subscribe: true
  scan:
    base-packages: com.lamp.atom.service.operator.consumer.controller

atom:
    schedule:
      operatorScheduleConfig:
        operatorScheduleKubernetesConfig:
          isUser: true
          masterUrl: https://{kubernetes_ip}:{port}/
          configName: kubernetesConfig.yaml
        operatorScheduleRpcConfig:
          namespace: atom-dev
          serverAddr: {nacos_ip}:{port}

decoration:
   defaultExceptionResult: com.lamp.atom.service.operator.consumers.utils.AtomConsumerExceptionResult
```

#### 2、启动算子模块的生产者

启动**atom-service-operator-provider模块**的main方法：

```java
com.lamp.atom.service.operator.provider.AtomServiceOperatorProviderApplication.java
```

#### 3、启动算子模块的消费者

启动**atom-service-operator-consumer模块**的main方法：

```java
com.lamp.atom.service.operator.consumer.AtomServiceOperatorConsumerApplication.java
```

## 三、启动Python的Runtime服务

启动atom-runtime-python模块的main.py





















