# 小马哥Spring Boot课堂笔记与代码

## 1. Spring Boot 初体验（Lesson1）

课堂链接：https://segmentfault.com/ls/1650000011387052

版本：1.4.2.RELEASE

### 元编程（Meta Programming）

在Java编程语言中，元编程是一种新型的变成模式，目的是减少代码行数，得到事半功倍的效果。

#### 主要模式
     注解驱动（Annotation-Driven）
     反射驱动（Reflection-Driven）
     表达式驱动（Expression-Driven）
     Lambda（Java 8 Introduced）
     Script On JVM（Groovy、JavaScript等）
#### 接口编程（Interface Programming）
    又称之为契约编程，在OOP语言中，其契约范围包括方法名称、方法入参（类型和顺序）、方法返回值（类型）以及异常情况等元数据。

### 面向服务架构（SOA） VS 微服务
####   类同
  ```
  面向服务（ Service-Oriented ）
  松耦合（Loose-Coupling）
  模块化（Modular）
  分布式计算（Distributed Computing）
  平台无关性（Independent Platform）
  ```
####   差异
   ```
   “原子性”（Atomic）
    领域驱动设计（DDD）
    开发运维体系（DevOps）
   ```
### DevOps
```
Dev In Spring Boot  = Services
Ops In Spring Boot = Management
Management = Endpoints
Endpoints = Monitoring And Control
```



