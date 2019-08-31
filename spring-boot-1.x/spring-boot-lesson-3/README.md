# Spring Boot Web篇（中）

## REST 理论基础

### 基本概念

https://en.wikipedia.org/wiki/Representational_state_transfer

REST：RESTful 
Representational State Transfer,is one way of providing interoperability between computer systems on the Internet.

### 历史(History)

REST 来自于Roy Thomas Fielding 2000年的博士论文 - 《Architectural Styles and the Design of Network-based Software Architectures》。

> Throughout the HTTP standardization process, I was called on to defend the design choices of the Web. That is an extremely difficult thing to do within a process that accepts proposals from anyone on a topic that was rapidly becoming the center of an entire industry. I had comments from well over 500 developers, many of whom were distinguished engineers with decades of experience, and I had to explain everything from the most abstract notions of Web interaction to the finest details of HTTP syntax. That process honed my model down to a core set of principles, properties, and constraints that are now called REST

#### 类似形式

Web Services：WSDL、SOAP

### 架构属性(Architectural properties)

- 性能（Performance）
  - performance in component interactions, which can be the dominant factor in user-perceived performance and network efficiency.
- 可伸缩性（Scalability）
  - [scalability](https://en.wikipedia.org/wiki/Scalability) allowing the support of large numbers of components and interactions among components.
- 统一接口简化性（Simplicity of a uniform Interface）
- 组件可修改性（Modifiability of components）
  - modifiability of components to meet changing needs (even while the application is running);
- 组件通讯可见性（Visibility of communication between components）
  - visibility of communication between components by service agents;
- 组件可移植性（Portability of component）
  - portability of components by moving program code with the data;
- 可靠性（Reliability）
  - reliability in the resistance to failure at the system level in the presence of failures within components, connectors, or data.

### 架构约束(Architectural constraints)

- C/S架构（Client-server architecture）

  - B/S其实也是C/S架构的一种

- 无状态（Statelessness）

  - HTTP协议就是无状态的
  - 数据库、缓存等其实是有状态的

  > The client-server communication is constrained by no client context being stored on the server between requests. Each request from any client contains all the information necessary to service the request, and the session state is held in the client. The session state can be transferred by the server to another service such as a database to maintain a persistent state for a period and allow authentication. The client begins sending requests when it is ready to make the transition to a new state. While one or more requests are outstanding, the client is considered to be *in transition*. The representation of each application state contains links that can be used the next time the client chooses to initiate a new state-transition.

- 可缓存（Cacheability）

  > As on the World Wide Web, clients and intermediaries can cache responses. Responses must therefore, implicitly or explicitly, define themselves as cacheable or not to prevent clients from getting stale or inappropriate data in response to further requests. Well-managed caching partially or completely eliminates some client-server interactions, further improving scalability and performance.

- 分层系统（Layered System）

- 按需代码（Code on demand）

  > Servers can temporarily extend or customize the functionality of a client by transferring executable code: for example, compiled components such as [Java applets](https://en.wikipedia.org/wiki/Java_applet), or client-side scripts such as [JavaScript](https://en.wikipedia.org/wiki/JavaScript).

- 统一接口（Uniform interface)

  > The uniform interface constraint is fundamental to the design of any RESTful system.It simplifies and decouples the architecture, which enables each part to evolve independently.

#### 统一接口（Uniform interface)

- 资源识别（Identification of resources）
- URI（Uniform Resource Identifier ）
- 资源操作（Manipulation of resources through representations）
  - HTTP verbs：GET、PUT、POST、DELETE
    - POST是非幂等，其他是幂等，所以在实际操作中是需要保证它的幂等性。
    - GET是取资源，PUT是更新资源，DELETE是删除资源，返回结果是相对固定的。
- 自描述消息（Self-descriptive messages）
  - Content-Type
  - MIME-Type
  - Media Type： application/javascript、 text/html
- 超媒体（HATEOAS）
  - Hypermedia As The Engine Of Application State

## REST服务端实践

### Spring Boot REST

#### 核心接口

##### 定义相关

- @Controller
  - 方法中如果没有@ResponseBody，则默认走模版渲染服务了。
- @RestController
  - @Controller + @ResponseBody

##### 映射相关

- @RequestMapping：可以定义HTTP Method
  - @GetMapping：Spring 4.3以后加入
  - @PostMapping：Spring 4.3以后加入
- @PathVariable
  - 参数变量时候需要用到

##### 请求相关

- @RequestParam
- @RequestHeader
- @CookieValue
- RequestEntity

##### 响应相关

- @ResponseBody
  - 返回的时候只有Body
- ResponseEntity
  - 返回Header + Body

#### 处理器方法参数解析（Spring MVC）

org.springframework.web.method.support.HandlerMethodArgumentResolver

### HATEOAS

HATEOAS = Hypermedia As The Engine Of Application State

增加了一种类似服务发现的方法，对比WebService。

### REST文档生成

Spring Boot /mappings endpoint

Spring RestDocs

Swagger

## REST客户端实践

### Web 浏览器

### Apache HttpClient

### Spring RestTemplate

