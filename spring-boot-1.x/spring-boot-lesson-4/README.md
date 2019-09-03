# Spring Boot Web篇（下）

## 传统Servlet回顾

### 什么是Servlet？

Servlet 是一种基于 Java 技术的 Web 组件，用于生成动态内容，由容器管理。类似于其他 Java 技术组件，Servlet 是平台无关的 Java 类组成，并且由 Java Web 服务器加载执行。

> A Java servlet processes or stores a [Java class](https://en.wikipedia.org/wiki/Java_class) in [Java EE](https://en.wikipedia.org/wiki/Java_EE) that conforms to the Java Servlet API,[[1\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-1) a standard for implementing Java classes that respond to requests. Servlets could in principle communicate over any [client–server](https://en.wikipedia.org/wiki/Client–server_model) protocol, but they are most often used with the [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol). Thus "servlet" is often used as shorthand for "HTTP servlet".[[2\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-r1-2) Thus, a [software developer](https://en.wikipedia.org/wiki/Software_developer) may use a servlet to add [dynamic content](https://en.wikipedia.org/wiki/Dynamic_web_page) to a [web server](https://en.wikipedia.org/wiki/Web_server) using the [Java platform](https://en.wikipedia.org/wiki/Java_platform). The generated content is commonly [HTML](https://en.wikipedia.org/wiki/HTML), but may be other data such as [XML](https://en.wikipedia.org/wiki/XML) and more commonly, JSON. Servlets can maintain [state](https://en.wikipedia.org/wiki/State_(computer_science)) in [session](https://en.wikipedia.org/wiki/Session_(computer_science)) variables across many server transactions by using [HTTP cookies](https://en.wikipedia.org/wiki/HTTP_cookie), or [URL mapping](https://en.wikipedia.org/wiki/URL_mapping).

Servlet也可以提供两种标准的Java Web Service，

- The [Java API for RESTful Web Services](https://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services) (JAX-RS 2.0) useful for AJAX, JSON and REST services
- The [Java API for XML Web Services](https://en.wikipedia.org/wiki/Java_API_for_XML_Web_Services) (JAX-WS) useful for [SOAP](https://en.wikipedia.org/wiki/SOAP) [Web Services](https://en.wikipedia.org/wiki/Web_Service)

### 什么是Servlet容器？

Servlet 容器，有时候也称作为 Servlet 引擎，作为Web服务器或应用服务器的一部分。通过请求和响应对话，提供 Web 客户端与 Servlets 交互的能力。容器管理Servlets实例以及它们的生命周期。

### 历史

1997年六月，Servlet 1.0 版本发行，最新版本 Servlet 4.0 处于研发状态。

> The Java servlets API was first publicly announced at the inaugural [JavaOne](https://en.wikipedia.org/wiki/JavaOne) conference in May 1996.[[4\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-4)[[5\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-5) About two months after the announcements at the conference, the first public implementation was made available on the JavaSoft website. This was the first alpha of the Java Web Server (JWS; then known by its codename *Jeeves*)[[6\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-6) which would eventually be shipped as a product on June 5, 1997.[[7\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-7)

| Servlet API version |                           Released                           |                        Specification                         |       Platform       |                      Important Changes                       |
| :-----------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :------------------: | :----------------------------------------------------------: |
|     Servlet 4.0     |                           Sep 2017                           |       [JSR 369](https://jcp.org/en/jsr/detail?id=369)        |      Java EE 8       |        [HTTP/2](https://en.wikipedia.org/wiki/HTTP/2)        |
|     Servlet 3.1     |                           May 2013                           |       [JSR 340](https://jcp.org/en/jsr/detail?id=340)        |      Java EE 7       | Non-blocking I/O, HTTP protocol upgrade mechanism ([WebSocket](https://en.wikipedia.org/wiki/WebSocket))[[14\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-14) |
|     Servlet 3.0     | [December 2009](http://www.javaworld.com/javaworld/jw-02-2009/jw-02-servlet3.html) |       [JSR 315](https://jcp.org/en/jsr/detail?id=315)        | Java EE 6, Java SE 6 | Pluggability, Ease of development, Async Servlet, Security, File Uploading |
|     Servlet 2.5     | [September 2005](http://www.javaworld.com/javaworld/jw-01-2006/jw-0102-servlet.html) |                           JSR 154                            | Java EE 5, Java SE 5 |           Requires Java SE 5, supports annotation            |
|     Servlet 2.4     | [November 2003](http://www.javaworld.com/jw-03-2003/jw-0328-servlet.html) |       [JSR 154](https://jcp.org/en/jsr/detail?id=154)        |  J2EE 1.4, J2SE 1.3  |                   web.xml uses XML Schema                    |
|     Servlet 2.3     | [August 2001](http://www.javaworld.com/jw-01-2001/jw-0126-servletapi.html) |        [JSR 53](https://jcp.org/en/jsr/detail?id=53)         |  J2EE 1.3, J2SE 1.2  |                     Addition of `Filter`                     |
|     Servlet 2.2     | [August 1999](http://www.javaworld.com/jw-10-1999/jw-10-servletapi.html) | [JSR 902](https://jcp.org/en/jsr/detail?id=902), [JSR 903](https://jcp.org/en/jsr/detail?id=903) |  J2EE 1.2, J2SE 1.2  | Becomes part of J2EE, introduced independent web applications in .war files |
|     Servlet 2.1     | [November 1998](http://www.javaworld.com/jw-12-1998/jw-12-servletapi.html) | [2.1a](https://web.archive.org/web/20090611171402/http://java.sun.com:80/products/servlet/2.1/servlet-2.1.pdf) |     Unspecified      | First official specification, added `RequestDispatcher`, `ServletContext` |
|     Servlet 2.0     |                        December 1997                         |                             N/A                              |       JDK 1.1        | Part of April 1998 Java Servlet Development Kit 2.0[[15\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-15) |
|     Servlet 1.0     |                        December 1996                         |                             N/A                              |                      | Part of June 1997 Java Servlet Development Kit (JSDK) 1.0[[9\]](https://en.wikipedia.org/wiki/Java_servlet#cite_note-Hunter200003-9) |



## Servlet 3.0前时代

### 服务组件

javax.servlet.Servlet

javax.servlet.Filter（since Servlet 2.3）

### 上下文组件

javax.servlet.ServletContext

javax.servlet.http.HttpSession

javax.servlet.http.HttpServletRequest

javax.servlet.http.HttpServletResponse

javax.servlet.http.Cookie（客户端）

### 配置

javax.servlet.ServletConfig

javax.servlet.FilterConfig（since Servlet 2.3 ）



### 输入输出

javax.servlet.ServletInputStream

javax.servlet.ServletOutputStream



### 异常

javax.servlet.ServletException



### 事件（since Servlet 2.3 ）

#### 生命周期类型

javax.servlet.ServletContextEvent

javax.servlet.http.HttpSessionEvent

java.servlet.ServletRequestEvent



#### 属性上下文类型

javax.servlet.ServletContextAttributeEvent

javax.servlet.http.HttpSessionBindingEvent

javax.servlet.ServletRequestAttributeEvent

### 监听器（since Servlet 2.3）

#### 生命周期类型

javax.servlet.ServletContextListener

javax.servlet.http.HttpSessionListener

javax.servlet.http.HttpSessionActivationListener

javax.servlet.ServletRequestListener

#### 属性上下文类型

javax.servlet.ServletContextAttributeListener

javax.servlet.http.HttpSessionAttributeListener

javax.servlet.http.HttpSessionBindingListener

javax.servlet.ServletRequestAttributeListener

### 组件申明注解

@javax.servlet.annotation.WebServlet

@javax.servlet.annotation.WebFilter

@javax.servlet.annotation.WebListener

@javax.servlet.annotation.ServletSecurity

@javax.servlet.annotation.HttpMethodConstraint

@javax.servlet.annotation.HttpConstraint

### 配置申明

@javax.servlet.annotation.WebInitParam

## Servlet 3.0 后时代

### 上下文

javax.servlet.AsyncContext



### 事件

javax.servlet.AsyncEvent



### 监听器

javax.servlet.AsyncListener

### Servlet 组件注册

javax.servlet.ServletContext#addServlet()

javax.servlet.ServletRegistration



### Filter 组件注册

javax.servlet.ServletContext#addFilter()

javax.servlet.FilterRegistration



### 监听器注册

javax.servlet.ServletContext#addListener()

javax.servlet.AsyncListener



### 自动装配

#### 初始器

javax.servlet.ServletContainerInitializer



#### 类型过滤

@javax.servlet.annotation.HandlesTypes



## 生命周期

### Servlet 生命周期

#### 初始化

当容器启动或者第一次执行时，Servlet#init(ServletConfig)方法被执行，初始化当前Servlet 。



#### 处理请求

当HTTP 请求到达容器时，Servlet#service(ServletRequest,ServletResponse) 方法被执行，来处理请求。



#### 销毁

当容器关闭时，容器将会调用Servlet#destroy 方法被执行，销毁当前Servlet。

### Filter 生命周期

#### 初始化

当容器启动时，Filter#init(FilterConfig)方法被执行，初始化当前Filter。



#### 处理请求

当HTTP 请求到达容器时，Filter#doFilter(ServletRequest,ServletResponse,FilterChain) 方法被执行，来拦截请求，在Servlet#service(ServletRequest,ServletResponse) 方法调用前执行。



#### 销毁

当容器关闭时，容器将会调用Filter#destroy 方法被执行，销毁当前Filter。



## Servlet on Spring Boot

### Servlet 组件扫描

@org.springframework.boot.web.servlet.ServletComponentScan

- 指定包路径扫描
  - String[] value() default {}
  - String[] basePackages() default {}

- 指定类扫描
  - Class<?>[] basePackageClasses() default {}

### 注解方式注册  

#### Servlet

- 扩展 javax.servlet.Servlet
  - javax.servlet.http.HttpServlet
  - org.springframework.web.servlet.FrameworkServlet

- 标记 @javax.servlet.annotation.WebServlet

#### Filter

- 实现 javax.servlet.Filter
  - org.springframework.web.filter.OncePerRequestFilter

- 标记 @javax.servlet.annotation.WebFilter

#### 监听器

- 实现Listener接口
  - javax.servlet.ServletContextListener
  - javax.servlet.http.HttpSessionListener
  - javax.servlet.http.HttpSessionActivationListener
  - javax.servlet.ServletRequestListener
  - javax.servlet.ServletContextAttributeListener
  - javax.servlet.http.HttpSessionAttributeListener
  - javax.servlet.http.HttpSessionBindingListener
  - javax.servlet.ServletRequestAttributeListener

- 标记 @javax.servlet.annotation.WebListener

### Spring Boot API方式注册

#### Servlet

- 扩展 javax.servlet.Servlet
  - javax.servlet.http.HttpServlet
  - org.springframework.web.servlet.FrameworkServlet

- 组装 Servlet
  - Spring Boot 1.4.0 开始支持
    - org.springframework.boot.web.servlet.ServletRegistrationBean
  - Spring Boot  1.4.0 之前
    - org.springframework.boot.context.embedded.ServletRegistrationBean

- 暴露 Spring Bean
  - @Bean

#### Filter

- 实现 javax.servlet.Filter
  - org.springframework.web.filter.OncePerRequestFilter
- 组装 Filter
  - Spring Boot 1.4.0 开始
    - org.springframework.boot.web.servlet.FilterRegistrationBean
  - Spring Boot  1.4.0 之前
    - org.springframework.boot.context.embedded.FilterRegistrationBean
- 暴露 Spring Bean
  - @Bean

#### 监听器

- 实现 Listener
- 组装 Listener
  - Spring Boot 1.4.0 开始
    - org.springframework.boot.web.servlet.ServletListenerRegistrationBean
  - Spring Boot  1.4.0 之前
    - org.springframework.boot.context.embedded.ServletListenerRegistrationBean
- 暴露 Spring Bean
  - @Bean

## JSP on Spring Boot

### 激活

#### 激活传统Servlet Web部署

Spring Boot 1.4.0 开始

org.springframework.boot.web.support.SpringBootServletInitializer

#### 组装 

org.springframework.boot.builder.SpringApplicationBuilder

#### 配置JSP视图

org.springframework.boot.autoconfigure.web.WebMvcProperties

```
spring.mvc.view.prefix
spring.mvc.view.suffix
```

