# spring-dynamic-gateway

基于SpringCloud Gateway的动态路由

### 技术栈

> SpringBoot、SpringCloud GateWay、SpringCloud Bus、SpringCloud Stream、SpringCloud Alibaba Nacos.<br>
> 消息队列kafka(RabbitMq)、Redis、持久化存储MySQL、通用API文档Springfox Swagger.<br>

### 项目结构

```
|--spring-dynamic-gateway
    |--livk-api-dynamic(动态路由操作服务)
        |--java(代码)
        |--resource
            |--http(idea Http Client测试请求)
            |--mapper(Mybatis xml文件)
            |--sql(初始化建表SQL与初始化表数据JSON)
    |--livk-api-gateway(网关服务)
    |--livk-api-monitor(SpringBoot监控服务)
    |--livk-cloud-common
        |--livk-common-core(通用核心基础包、内嵌Bus，根据Bus starter导入自动激活)
        |--livk-common-gateway(Gateway路由信息整合Redis处理)
        |--livk-common-log(日志纪录操作相关)
        |--livk-common-pom(common聚合POM依赖)
        |--livk-common-redis(Redis与Spring Cache操作相关)
        |--livk-common-swagger(Swagger与Gateway聚合配置，Swagger单服务配置)
```

### 环境搭建

> 全局采用docker搭建环境<br>
> Mysql、Redis、kafka(RabbitMq)百度自行搭建<br>
> Nacos(2.0.3)集群搭建，配置Nginx转发<br>
> [Nacos官方](https://nacos.io/zh-cn/)

```shell
docker run -d \   
-e PREFER_HOST_MODE=hostname \
-e MODE=cluster \
-e NACOS_APPLICATION_PORT=8844 \  
-e NACOS_SERVERS="192.168.75.128:8844 192.168.75.128:8846 192.168.75.128:8848" \ 
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=192.168.75.128 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123456 \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e NACOS_SERVER_IP=192.168.75.128 \
-e JVM_XMS=256m \
-e JVM_XMX=512m \
-p 8844:8844 \
-p 7844:7844 \
-p 9844:9844 \
-p 9845:9845 \
--name nacos1 \
nacos/nacos-server:v2.0.3

docker run -d \
-e PREFER_HOST_MODE=hostname \
-e MODE=cluster \
-e NACOS_APPLICATION_PORT=8846 \
-e NACOS_SERVERS="192.168.75.128:8844 192.168.75.128:8846 192.168.75.128:8848" \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=192.168.75.128 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123456 \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e NACOS_SERVER_IP=192.168.75.128 \
-e JVM_XMS=256m \
-e JVM_XMX=512m \
-p 8846:8846 \
-p 7846:7846 \
-p 9846:9846 \
-p 9847:9847 \
--name nacos2 \
nacos/nacos-server:v2.0.3

docker run -d \
-e PREFER_HOST_MODE=hostname \
-e MODE=cluster \
-e NACOS_APPLICATION_PORT=8848 \
-e NACOS_SERVERS="192.168.75.128:8844 192.168.75.128:8846 192.168.75.128:8848" \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=192.168.75.128 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123456 \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e NACOS_SERVER_IP=192.168.75.128 \
-e JVM_XMS=256m \
-e JVM_XMX=512m \
-p 8848:8848 \
-p 7848:7848 \
-p 9848:9848 \
-p 9849:9848 \
--name nacos3 \
nacos/nacos-server:v2.0.3
```

Nginx转发(配置在http下面)

```shell
docker run --name nginx-nacos -p 9966:9966 -d nginx

配置
upstream nacos {   
    server 192.168.75.128:8844;
    server 192.168.75.128:8846;   
    server 192.168.75.128:8848;
}
server{
	listen 9966;
	location / {
		proxy_pass http://nacos;
	}
}
```

### 0.0.1

> 表SQL详见[SQL](./table.sql).<br>
> 每一个XXXAutoConfiguration，请注意bean生成的条件，非必要不要修改，以免服务启动报错.<br>
> Spring Cloud Bus需要与Spring Cloud Stream结合使用，同时需要一个Message Queue(官方使用RabbitMQ或者Kafka).<br>
> [Gateway路由信息与Redis操作结合](./livk-cloud-common/livk-common-gateway/src/main/java/com/livk/common/gateway/support/RedisRouteDefinitionWriter.java).<br>
> [Bus操作远程Event](./livk-cloud-common/livk-common-bus/src/main/java/com/livk/common/bus/event/LivkRemoteEvent.java).<br>
> [Mapstruct转化器通用接口](./livk-cloud-common/livk-common-core/src/main/java/com/livk/common/core/converter/BaseConverter.java).<br>
> [Swagger整合Gateway](./livk-cloud-common/livk-common-swagger/src/main/java/com/livk/common/swagger/support/GatewaySwaggerResourcesProvider.java).<br>
