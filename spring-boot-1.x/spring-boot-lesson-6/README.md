# Spring Boot 嵌入式Web容器

## 1. 传统Servlet容器

### 1.1 Eclipse Jetty

https://www.eclipse.org/jetty/about.html

> Eclipse Jetty Web Server provides an HTTP server and Servlet container capable of serving static and dynamic content either from a standalone or embedded instantiations. From jetty-7 on, the jetty web-server and other core components are hosted by the Eclipse Foundation. The project provides:
>
> - Asynchronous HTTP Server
> - Standards based Servlet Container
> - websocket server
> - http/2 server
> - Asynchronous Client (http/1.1, http/2, websocket)
> - OSGI, JNDI, JMX, JASPI, AJP support

### 1.2 Apache Tomcat

Tomcat是由Apache软件基金会下属的Jakarta项目开发的一个Servlet容器，按照Sun Microsystems提供的技术规范，实现了对Servlet和JavaServer Page（JSP）的支持，并提供了作为Web服务器的一些特有功能，如Tomcat管理和控制平台、安全域管理和Tomcat阀等。由于Tomcat本身也内含了一个HTTP服务器，它也可以被视作一个单独的Web服务器。

> The Apache Tomcat® software is an open source implementation of the Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket technologies. The Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket specifications are developed under the [Java Community Process](http://jcp.org/en/introduction/overview).
>
> Apache Tomcat, Tomcat, Apache, the Apache feather, and the Apache Tomcat project logo are trademarks of the Apache Software Foundation.

#### 1.2.1 核心组件（Components)

- Engine
- Host
- Context

#### 1.2.2 静态资源处理

处理静态资源的Servlet为：`org.apache.catalina.servlets.DefaultServlet`,定义在tomcat中的web.xml中。

```xml
<servlet>
    <servlet-name>default</servlet-name>
    <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
    <init-param>
        <param-name>debug</param-name>
        <param-value>0</param-value>
    </init-param>
    <init-param>
        <param-name>listings</param-name>
        <param-value>false</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
```

#### 1.2.3 欢迎页面（Welcome file list）

配置的默认首页

#### 1.2.4 JSP处理

Jsp处理servlet为 `org.apache.jasper.servlet.JspServlet`,配置如下：

```xml
<servlet>
    <servlet-name>jsp</servlet-name>
    <servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
    <init-param>
        <param-name>fork</param-name>
        <param-value>false</param-value>
    </init-param>
    <init-param>
        <param-name>xpoweredBy</param-name>
        <param-value>false</param-value>
    </init-param>
    <load-on-startup>3</load-on-startup>
</servlet>
```



#### 1.2.5 类加载（Classloading）

- Bootstrap ClassLoader
- System ClassLoader
- Common ClassLoader
- Webapp ClassLoader

#### 1.2.6 连接器（Connectors）

- 端口（port）
- 协议（protocol）
- 线程池（Thread Pool）
- 超时时间（Timeout）

#### 1.2.7 JDBC 数据源（DataSource）

#### 1.2.8 JNDI（Java Naming and Directory Interface）

- 基本类型
  - 资源（Resource）
  - 环境（Environment）
- 配置方式
  - context.xml 配置
  - web.xml 配置

## 2. Spring Boot 嵌入式Web容器

### 2.1 Servlet 容器

- Embedded Jetty
- Embedded Tomcat
  - org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
  - org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer

### 2.2 非 Servlet 容器

- Undertow