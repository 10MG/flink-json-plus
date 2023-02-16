# flink-json-plus

<p align="left">
    <a href="https://mvnrepository.com/artifact/cn.tenmg/flink-json-plus">
        <img alt="maven" src="https://img.shields.io/maven-central/v/cn.tenmg/flink-json-plus.svg?style=flat-square">
    </a>
    <a target="_blank" href="LICENSE"><img src="https://img.shields.io/:license-Apache%202.0-blue.svg"></a>
</p>

## 介绍
flink-json的增强版，可使用Flink SQL解析Debezium采集数据的`op`属性，解决官方版本的flink-json无法获取op属性的问题。


## 安装教程

1.  如果使用Flink SQL客户端，则将JAR包上传至flink的lib目录下，重启flink即可使用
2.  如果使用Table API，则除将JAR包上传至flink的lib目录及重启flink外，还需要在项目中引入该JAR。以Maven项目为例（其中${flink-json-plus.version}为版本号，可定义属性或直接使用版本号替换）：

```
<!-- https://mvnrepository.com/artifact/cn.tenmg/flink-json-plus -->
<dependency>
    <groupId>cn.tenmg</groupId>
    <artifactId>flink-json-plus</artifactId>
    <version>${flink-json-plus.version}</version>
</dependency>
```


## 使用说明

例如希望将Debezium采集的test表的数据的`op`属性解析出来，可以使用如下Flink SQL创建源表（Source Table）：

```
CREATE TABLE test(
  id STRING NOT NULL,
  name STRING NOT NULL,
  op STRING
)
WITH (
  'properties.bootstrap.servers' = 'kafka1:9092,kafka2:9092,kafka3:9092',
  'properties.group.id' = 'flink-jobs-data-sync.test',
  'topic' = 'test.testdb.test', 'connector' = 'kafka',
  'scan.startup.mode' = 'earliest-offset',
  'format'='debezium-json-plus'
)

```

## 支持格式

### Debezium

[Debezium](https://debezium.io) 是一个 CDC（Changelog Data Capture，变更数据捕获）的工具，可以把来自 MySQL、PostgreSQL、Oracle、Microsoft SQL Server 和许多其他数据库的更改实时流式传输到 Kafka 中。 Debezium 为变更日志提供了统一的格式结构，并支持使用 JSON 和 Apache Avro 序列化消息。

flink-json-plus 目前支持将 Debezium JSON 消息解析为 INSERT / UPDATE / DELETE 消息到 Flink SQL 系统中。在很多情况下，利用这个特性非常的有用，例如

- 将增量数据从数据库同步到其他系统
- 日志审计
- 数据库的实时物化视图
- 关联维度数据库的变更历史，等等。

flink-json-plus 还支持将 Flink SQL 中的 INSERT / UPDATE / DELETE 消息编码为 Debezium 格式的 JSON 消息，输出到 Kafka 等存储中。 但需要注意的是，目前 flink-json-plus 还不支持将 UPDATE_BEFORE 和 UPDATE_AFTER 合并为一条 UPDATE 消息。因此，flink-json-plus 和 flink-json 一样，将 UPDATE_BEFORE 和 UPDATE_AFTER 分别编码为 DELETE 和 INSERT 类型的 Debezium 消息。

 _注意：_  请参考 Debezium 文档，了解如何设置 Debezium Kafka Connect 用来将变更日志同步到 Kafka 主题。

## 问题解答

### SQL Client


 **问题描述：** 在Flink SQL Client 中使用 [flink-json-plus](https://gitee.com/tenmg/flink-json-plus)，加入 [flink-json-plus](https://gitee.com/tenmg/flink-json-plus) 依赖后。

1. 创建源表

```
DROP TABLE IF EXISTS table_process;
CREATE TABLE table_process (
id BIGINT,
name STRING,
create_time TIMESTAMP,
update_time TIMESTAMP
) WITH (
'properties.bootstrap.servers' = 'hadoop101:9092',
'topic' = 'input_kafka',
'connector' = 'kafka',
'scan.startup.mode' = 'earliest-offset',
'format'='debezium-json-plus'
);
```


2. 执行查询

```
select * from table_process;
```

3. 发生异常

```
Could not initialize class cn.tenmg.flink.formats.json.debezium.DebeziumJsonPlusFormatOptions
```


 **解决方案：** [https://gitee.com/tenmg/flink-json-plus/issues/I6FGR7](https://gitee.com/tenmg/flink-json-plus/issues/I6FGR7)

## 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
