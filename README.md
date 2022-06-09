# flink-json-plus

#### 介绍
flink-json的增强版，可使用Flink SQL解析Debezium采集数据的`op`属性，解决官方版本的flink-json无法获取op属性的问题。


#### 安装教程

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


#### 使用说明

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

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
