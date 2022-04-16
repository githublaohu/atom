# atom
lampup小组下的atom项目是一个为AI服务的可编排自动化运行平台。


## 特性

* 自动运行训练，推理，特征，数据算子

* 支持文件算子（超大文件预下载与并行下载，多个算子需要同一个文件，重复下载非常浪费资源与时间）【未开始】

* 提供基于python实现能做基本数据操作的数据算子【未开始】

* 支持算子本地开发

* 多租户下 kubernetes深度管理【进行中】

* 支持多种调用（k8s,简单）与运行模式（命令启动，docker）【进行中】

* 可以从各种数据源（MySQL，Ceph，hbase.....）读取数据给算子提供“养料”，并且把算子产生的数据写入各种存储

* 可操作，可视，支持多租户的控制台

* 可对算子，连接，数据源，模型等进行深度管理

* 支持operator 在runtime的隔离与SOA【开发中，基本完成】

* 支持推理多语言SDK【开发中，】

* 推荐功能【进行中，预计三月初】

* 自动运行flink算子【未开始】

* 支持运行编排【未开始】

* 支持推理编排【未开始】

* 标注功能【未开始】

* A/B Test 【未开始】


## 组成

* atom-python-runtime  运行训练，推理，特征，数据算子

* atom-service 各种业务模块

* 调用组件 负责调用flink，python-runtime

## 依赖

* nacos

* electron

* decoration light（atom-service，java-sdk）

* MySQL

## Installation
```shell
pip install atom-runtime
```