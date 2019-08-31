# Spring Boot Web篇（上）

## 静态Web内容

### 概念

HTTP 请求内容由Web服务器文件系统提供，常见静态Web内容如：HTML、CSS、JS、JPEG、Flash等等。

### 特点

- 计算类型：I/O 类型
- 交互方式：单一
- 资源内容：相同（基本）
- 资源路径：物理路径（文件、目录）
- 请求方法：GET（主要）

### 常见使用场景

- 信息展示
- 样式文件（CSS）
- 脚本文件（JS）
- 图片（JPEG、GIF）
- 多媒体（Flash、Movie）
- 文件下载

### 常见Web服务器

- Apache HTTP Server
- Nginx
- Microsoft IIS
- GWS

### 标准优化技术

#### 资源变化

响应头：Last-Modified

请求头：If-Modified-Since

#### 资源缓存

响应头：ETag

请求头：If-None-Match

### 思考

为什么 Java Web Server 不是常用 Web Server？

- 内存占用
  - 类型
  - 分配
- 垃圾回收
  - 被动回收
  - 停顿
- 并发处理
  - 线程池
  - 线程开销

## 动态Web内容

### 概念

与静态 Web内容不同，请求内容通过服务器计算而来。

### 特点

- 计算类型：混合类型（I/O、CPU、内存等）
- 交互方式：丰富（用户输入、客户端特征等）
- 资源内容：多样性
- 资源路径：逻辑路径（虚拟）
- 请求方法：GET、HEAD、PUT、POST等

### 常见使用场景

- 页面渲染
- 表单交互（Form）
- AJAX
- XML
- JSON/JSONP
- Web Services(SOAP、WSDL)
- WebSocket

### 流行 Java Web 服务器

- Servlet 容器（Tomcat、Jetty）
- 非 Servlet容器（Undertow）

### 请求

- 资源定位（URI）
- 请求协议（Protocol）
- 请求方法（Method）
- 请求参数（Parameter）
- 请求主体（Body）
- 请求头（Header）
- Cookie

### 响应

- 响应头（Header）
- 响应主体（Body）

### 技术/架构演变

- CGI（Common Gateway Interface）
- Servlet
- JSP（Java Server Page)
- Model 1（JSP + Servlet + JavaBeans）
- Model 2（MVC）

## 模版引擎

### JSP

### Velocity

Spring Boot 1.5+后被移除了。

### Thymeleaf