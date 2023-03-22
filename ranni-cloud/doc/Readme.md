## ranni项目文档说明


#### model

#### nacos
 nacosInit.sql：[初始化sql脚本](https://github.com/alibaba/nacos/blob/master/config/src/main/resources/META-INF/nacos-db.sql)
 
 seataInit.sql：seata使用nacos配置中心脚本


#### seata
 nacos-config.sh：使用nacos配种中心上传脚本
 
 nacos-config.txt：seata配置文件(放在seata根目录，执行上传脚本)

#### minio-docker
```dockerfile
docker run -p 9000:9000 -p 9090:9090 -d --name minio \
  -e "MINIO_ACCESS_KEY=minio123456" \
  -e "MINIO_SECRET_KEY=minio123456" \
  -v ~/docker/data/minio:/data \
  -v ~/docker/conf/minio:/root/.minio \
  minio/minio server /data --console-address ":9090" -address ":9000"
```

#### xxl-job-admin
```dockerfile
docker run --network=mynet 
-e PARAMS="--spring.datasource.url=jdbc:mysql://mysql-master:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai --spring.datasource.username=root --spring.datasource.password=root" 
-p 9911:8080 -v ~/docker/logs/xxl-job/:/data/applogs --name xxl-job-admin  -d xuxueli/xxl-job-admin:2.3.0
```

#### kafka
```dockerfile
#查看kafka主题列表
kafka-topics.sh  --bootstrap-server localhost:9092 --list
#创建主题 orderStatus 分区1副本2
kafka-topics.sh  --bootstrap-server localhost:9092 --create --partitions 2 --replication-factor 1 --topic orderStatus
#查看topic详情
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic orderStatus
#删除topic
kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic orderStatus

#发送消息
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic orderStatus
#发送消息的其他参数
--property "parse.key=true" --property "key.separator=:"


#消费消息
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic orderStatus
#消费消息的其他参数
--from-beginning 从头开始消费
--group 指定消费者组名称

```
