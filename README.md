## 简介
skyeboot 是一个基于Spring Boot & MyBatis的纯后端项目，可用于管理平台和快速构建中小型API、RESTful API项目；
## 特征
+ 基于SpringBoot，MybBtis，MySQL
+ 基于RBAC的权限管理，使用Shiro实现，认证和授权数据使用redis进行缓存
+ 全局禁用session，采用JWT token和redis代替
+ 统一异常处理，统一响应结果封装
+ AOP注解式系统日志
+ 构建工具使用Gradle管理，多模块项目
+ 代码生成器生成mapper文件、接口和实体类，CRUD方法抽象封装
+ RESTful风格api接口，后端validation验证请求数据

## 项目结构
```
skyeboot
├─hejinyo-common 公共模块
│  ├─annotation 自定义注解
│  ├─base 抽象类，基础类
│  ├─consts 枚举
│  ├─exception 异常处理
│  ├─utils 工具类
│  └─validator 验证分组接口
│ 
├─hejinyo-generator 代码生成模块  
│ 
├─hejinyo-skye 系统主模块 
│  ├─aspect  系统日志AOP
│  ├─config  配置类（springboot,shiro,redis配置）
│  ├─shiro  shiro定制
│     ├─cache shiro的cachemanage,未使用
│     ├─filter  自定义拦截器
│     ├─realm
│     ├─subject 
│     └─token 自定义shiro token,用于分离多用户模式  
│  └─utils 工具类
│ 
├─hejinyo-wechat 微信后台模块（测试） 
```
## 技术选型&文档
- Spring Boot（[查看Spring Boot学习&使用指南](http://www.jianshu.com/p/1a9fd8936bd8)）
- MyBatis（[查看官方中文文档](http://www.mybatis.org/mybatis-3/zh/index.html)）
- MyBatis PageHelper分页插件（[查看官方中文文档](https://pagehelper.github.io/)）
- Druid Spring Boot Starter（[查看官方中文文档](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter/)）
- Fastjson（[查看官方中文文档](https://github.com/Alibaba/fastjson/wiki/%E9%A6%96%E9%A1%B5)）
- 其他略
## 测试地址
- 前端：（[skye.hejinyo.cn](http://skye.hejinyo.cn)）
- 后端：（[api.hejinyo.cn](http://api.hejinyo.cn)）
