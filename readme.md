### Kobep

------

基于Springcloud Alibaba，Springsecurity+Ouath2+jwt的一个简单小demo

------

### 技术栈

- 注册中心：Nacos
- 服务网关：Spring cloud-Gateway
- 配置中心：Nacos
- 服务调用：Spring-cloud-open-Feign
- 熔断降级：Sentinel
- 消息队列：RabbitMQ
- 权限认证：Spring secruity Oauth2
- 部署：Docker + gogs + jenkins
- 持久层：mybatis plus（jpa）都有

### 项目结构说明

- kobep-auth 授权中心
- kobep-back 资源服务（mybatis-plus demo）
- kobep-common 公共服务
- kobep-gateway 网关
- kobep-jpa 集成jpa demo
- kobep-mq 集成mq demo