# 深入浅出Spring Boot管控

## 1. JMX

JMX（Java Management Extensions）技术提供构建分布式、Web、模块化的工具，以及管理和监控设备和应用的动态解决方案。从 Java 5 开始，JMX API 作为 Java 平台的一部分。

根据JMX规范（[JSR-03](https://www.jcp.org/en/jsr/detail?id=3)）可以知道JMX分为以下三部分：

- 设备层
- 代理层
- 分布式服务层

> The JMX architecture is divided into three levels:
> ■ Instrumentation level
> ■ Agent level
> ■ Distributed services level

![1548556486361](C:\Users\Landy\AppData\Roaming\Typora\typora-user-images\1548556486361.png)

### 1.1 Instrumentation level

- MBeans (standard, dynamic, open, and model MBeans)
- Notification model
- MBean metadata classes
  - 元数据信息（比如Field，Method等）是放在Java的永久区（MetaSpace）

### 1.2 Agent level

- MBean server
- Agent services

### 1.3 JMX运用场景

- 线上应用的数据监控
- 动态修改线上应用的缓存等

## 2. Managed Beans (MBeans)

### 2.1 MBeans组成

The management interface of an MBean is represented as:

- Valued attributes that can be accessed
- Operations that can be invoked
- Notifications that can be emitted (see “Notification Model” on page 29) 
  - 事件机制
- The constructors for the MBean’s Java class

### 2.2 MBeans类型

> The JMX specification defines four types of MBean: standard, dynamic, open and
> model MBeans. Each of these corresponds to a different instrumentation need.

#### 2.2.1 Standard MBeans

  设计和实现最为简单，Bean的管理 通过接口方法来描述。MXBean 是一种特殊标准MBean，它使用开放MBean的概念，允许通用管理，同时简化编码。

> Standard MBeans are the simplest to design and implement, their management
> interface is described by their method names. MXBeansare a kind of Standard
> MBean that uses concepts from Open MBeans to allow universal manageability
> while simplifying coding.

#### 2.2.2 Dynamic MBeans

必须实现指定的接口，不过它在运行时能让管理接口发挥最大弹性。

> Dynamic MBeans must implement a specific interface, but they expose their
> management interface at runtime for greatest flexibility.

#### 2.2.3 Open MBeans

> Open MBeans are dynamic MBeans that rely on basic data types for universal
> manageability and that are self describing for user-friendliness.

#### 2.2.4 Model MBeans

>  Model MBeansare also dynamic MBeans that are fully configurable and self
> described at runtime; they provide a generic MBean class with default behavior
> for dynamic instrumentation of resource.

### 2.3 MBeans操作

#### 2.3.1 Standard MBeans

接口必须以MBean结尾，实现类必须没有MBean结尾，并且是接口的一部分。

接口定义如下：

```java
public interface HelloMBean {

    String greeting();

    void setValue(String value);

    String getValue();
}
```

实现类定义如下：

```java
/**
 * 标准Bean实现的接口必须以MBean结尾
 */
public class Hello implements HelloMBean {
    private String value;

    @Override
    public String greeting() {
        return "hello world";
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
```

客户端定义如下：

```java
public class HelloMBeanServer {

    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = new ObjectName("org.landy.springbootjmx:type=Hello");

        mBeanServer.registerMBean(new Hello(), objectName);

        System.out.println("HelloMBeanServer is starting....");
        //为了便于测试,可以用JConsole查看运行结果
        TimeUnit.HOURS.sleep(1);
    }

}
```

#### 2.3.2 Dynamic MBeans

只需要定义自己的接口与实现类，跟MBean没有强关联，只需要通过接口的方式进行暴露。

客户端定义如下：

```java
public class DynamicMBeanServer {

    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = new ObjectName("org.landy.springbootjmx.dynamic:type=Data");

        Data data = new DataManagedBean();
        //利用动态Bean就非常具有弹性空间了，Bean只要实现自己的接口即可。
        DynamicMBean dynamicMBean = new StandardMBean(data,Data.class);

        mBeanServer.registerMBean(dynamicMBean, objectName);

        System.out.println("DynamicMBeanServer is starting....");
        //为了便于测试,可以用JConsole查看运行结果
        TimeUnit.HOURS.sleep(1);

    }

}
```



## 3. Spring/Spring Boot应用

### 3.1 Spring JMX

Spring Boot是基于Spring Framework的，而Spring Framework是很多Java SE和Java EE很多特性（规范）的框架。

结合Spring Framework[官方文档](https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/integration.html#jmx)，可以知道：

> The JMX (Java Management Extensions) support in Spring provides features that let you easily and transparently integrate your Spring application into a JMX infrastructure.

### 3.2 Spring Boot JMX

Spring Boot[官方文档](https://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/htmlsingle/#boot-features-jmx)可以知道：

> Java Management Extensions (JMX) provide a standard mechanism to monitor and manage applications. By default, Spring Boot creates an `MBeanServer` bean with an ID of `mbeanServer` and exposes any of your beans that are annotated with Spring JMX annotations (`@ManagedResource`, `@ManagedAttribute`, or `@ManagedOperation`).
>
> See the [`JmxAutoConfiguration`](https://github.com/spring-projects/spring-boot/tree/v2.1.2.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jmx/JmxAutoConfiguration.java) class for more details.

### 3.3 通过HTTP的方式暴露JMX

Jolokia官网：https://jolokia.org/reference/html/index.html 

#### 3.3.1  Using Jolokia for JMX over HTTP

Jolokia is a JMX-HTTP bridge that provides an alternative method of accessing JMX beans. To use Jolokia, include a dependency to `org.jolokia:jolokia-core`. For example, with Maven, you would add the following dependency:

```
<dependency>
	<groupId>org.jolokia</groupId>
	<artifactId>jolokia-core</artifactId>
</dependency>
```

The Jolokia endpoint can then be exposed by adding `jolokia` or `*` to the `management.endpoints.web.exposure.include` property. You can then access it by using `/actuator/jolokia` on your management HTTP server.

#### 3.3.2 演示

请求案例：https://jolokia.org/reference/html/protocol.html#get-request

访问方式： http://localhost:8080/jolokia/read/java.lang:type=Memory/HeapMemoryUsage 

代码请参见github

1. 执行：http://localhost:8080/person?name=landy&description=home
2. 执行：http://localhost:8080/jolokia/read/org.landy.springbootjmx.spring:type=Person,name=person

即可看到第一步执行的结果，即可以准实时监控。

### 4 JMX扩展

- 通过命令访问JMX
  - jstack pid
  - Jconsole --性能监控
  - Jmap
- Spring Boot适合做微服务
- 