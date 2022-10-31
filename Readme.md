## 系统说明

基于springcloudAlibaba搭建的hello world后端项目，用于熟悉组件的基本用法



## 系统介绍

### 进度

- ~~集成mybatis-plus，基础orm操作和动态数据源~~
- ~~集成lettuce和redission，分布式锁util~~
- ~~集成nacos，用作注册中心和配置中心~~
- ~~集成seata，使用AT模式实现分布式事务~~
- ~~集成sentinel，在网关做限流~~
- ~~集成knife4j文档~~
- ~~基于freemarker，实现基础代码生成~~
- ~~skywalking链路监控，agent采集不用修改代码，docker环境搭建~~
- log-starter模块
    - appender功能，集成logstash或filebeat发送到ELK
- ~~logstater模块，datapermission数据权限统一处理~~

- mq-starter（rabbitmq）
- ~~elastic-starter~~
- sentinel功能完善
    - 集群限流，需要搭建sentinel token server
    - Sentinel-dashboard规则修改和nacos的同步
- ~~集成xxl-job~~
- security功能
    - security-starter模块
    - oauth2、jwt
- 应用监控（spring-admin？prometheus？）



### 核心依赖

```
| 依赖                   | 版本         |
| ---------------------- |------------|
| Spring Boot            | 2.3.12.RELEASE      |
| Spring Cloud           | Spring Cloud Hoxton.SR12   |
| Spring Cloud Alibaba   | 2.2.7.RELEASE |
| sentinel               | 2.3.6      |
| Mybatis Plus           | 3.4.2      |
| seata                  | 1.3.0     |
| nacos                  | 2.0.3     |
```

### 模块说明

~~~lu
ranni
├── ranni-cloud -- 项目pom依赖，文档，docker和sql脚本
├── ranni-component -- 相关starter
    ├── ranni-base -- 基础pom依赖、DTO、UTIL
    ├── ranni-component-datasource-starter -- 数据库starter
    ├── ranni-component-doc-starter -- 文档starter
    ├── ranni-component-elastic-starter -- es搜索-starter
    ├── ranni-component-executor-starter -- 单机定时任务-starter
    ├── ranni-component-log-starter -- 日志starter
    ├── ranni-component-oss-starter -- 对象存储starter
    ├── ranni-component-redis-starter -- 缓存starter
    └── ranni-component-xxljob-starter -- 分布式调度starter
└── ranni-service -- 后台服务
    ├── ranni-code-generator -- 代码生成模块
    ├── ranni-gateway -- 网关
    ├── ranni-service-api -- api系统，业务聚合层
    ├── ranni-service-crm -- 会员系统
    ├── ranni-service-order -- 订单系统
    └── ranni-service-stock -- 库存系统
~~~

### 本地运行

#### 数据脚本

//todo

#### docker环境

//todo

