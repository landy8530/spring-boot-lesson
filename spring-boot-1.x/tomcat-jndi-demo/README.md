# Tomcat使用JNDI配置数据源

## 1. JNDI简介

### 1.1 定义

JNDI就是Sun提出的一套对象命名和目录服务的接口，全称为`Java Naming and Directory Interface`，简单的说就是JNDI通过目录服务的基础上抽象了一层来查找Java对象。引用维基百科中的定义如下：

> The **Java Naming and Directory Interface** (**JNDI**) is a Java [API](https://en.wikipedia.org/wiki/Application_programming_interface) for a [directory service](https://en.wikipedia.org/wiki/Directory_service) that allows Java software clients to discover and look up data and resources (in the form of Java [objects](https://en.wikipedia.org/wiki/Object_(computer_science))) via a name. Like all [Java](https://en.wikipedia.org/wiki/Java_(programming_language)) APIs that interface with host systems, JNDI is independent of the underlying implementation. Additionally, it specifies a [service provider interface](https://en.wikipedia.org/wiki/Service_provider_interface) (SPI) that allows [directory service](https://en.wikipedia.org/wiki/Directory_service) implementations to be plugged into the framework.[[1\]](https://en.wikipedia.org/wiki/Java_Naming_and_Directory_Interface#cite_note-1) The information looked up via JNDI may be supplied by a server, a flat file, or a database; the choice is up to the implementation used.

根据他的定义，可以知道JNDI是通过SPI作为插件的方式应用于框架当中，通过JNDI查找的对象可以通过服务器，文件，或者数据库提供，这个是取决于具体的实现即可。

### 1.2 没有JNDI怎么办

在没有JNDI的时候，比如需要连接一个Mysql数据库，则需要通过硬编码的方式达到连接数据库的目的。如下代码所示，

```java
 Class.forName("com.mysql.jdbc.Driver",true,Thread.currentThread().getContextClassLoader());
  Connection conn = DriverManager.getConnection("jdbc:mysql://test?user=landy&password=123456");
```

这样做的情况，就是等到需求改变的时候不容易修改，比如服务名称，数据库用户名密码等，甚者连接池参数等都可能修改。

### 1.3 JNDI使用场景

有了JNDI以后，程序员就只要关心自己的实现即可，不需要关注具体的数据库如何连接，如何配置用户名密码等操作。主要应用场景根据维基百科可以知道有以下几种：

> 1. Connecting a Java application to an external directory service (such as an address database or an [LDAP](https://en.wikipedia.org/wiki/LDAP) server)
> 2. Allowing a [Java Servlet](https://en.wikipedia.org/wiki/Java_Servlet) to look up configuration information provided by the hosting web container.

总结起来就是两点，一点是连接数据库或者LDAP Server,第二个就是允许Java Servlet寻找Web容器提供的配置信息，其实这点就相当于我可以把数据库的连接配置信息也配置在Servlet 容器中，达到开发人员与运维人员解耦的要求。

## 2. Tomcat配置JNDI

Tomcat配置JNDI主要是配置server.xml和context.xml，主要有三种方式配置，可以参考文章[**tomcat下jndi的三种配置方式**](https://blog.csdn.net/lgm277531070/article/details/6711177) ,本文采用他的全局配置方式，但是有点区别，笔者应用于真正的生产环境既是此种方式。我采用了独立于真正的tomcat容器，然后通过脚本指向tomcat，然后启动即可。

### 2.1 Tomcat配置总览

根据tomcat官网介绍，Tomcat提供了一套与Java EE标准兼容的模式为其下运行的每个Web应用程序提供JNDI InitialContext实现实例，然后Java EE标准在WEB-INF/web.xml中定义了一组标准的元素用于引用或者定义相应的资源。

> Tomcat provides a JNDI **InitialContext** implementation instance for each web application running under it, in a manner that is compatible with those provided by a [Java Enterprise Edition](http://www.oracle.com/technetwork/java/javaee/overview/index.html) application server. The Java EE standard provides a standard set of elements in the `/WEB-INF/web.xml` file to reference/define resources.

其实我们可以把tomcat的各个目录拷贝到另外一个目录下，比如如下目录所示：

![1568523204758](https://github.com/landy8530/spring-boot-lesson/blob/master/doc/spring-boot-1.x/jndi/1568523204758.png)

bin目录主要一个start.bat的批处理文件，conf目录下就把所有的原有tomcat/conf目录下的文件拷贝至此即可，然后修改server.xml和context.xml，至于logs、webapps、work目录作为空目录存在即可。

### 2.2 startup.bat配置

本配置文件主要是配置JVM启动参数和引导启动tomcat容器，配置如下：

```bat
set "JAVA_HOME=C:\01_soft\java\jdk1.8.0_202"
set "CATALINA_HOME=C:\05_webserver\apache-tomcat-8.5.45"
set "CATALINA_BASE=C:\03_code\idea_workspace\spring-boot-lesson\tomcat-instances\tomcat-jndi"
set "TITLE=Tomcat JNDI Demo"
SET CATALINA_OPTS=-server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=7776
set "JAVA_OPTS=%JAVA_OPTS% -Dlog.path=C:\03_code\idea_workspace\spring-boot-lesson\tomcat-instances\tomcat-jndi\logs -server -Xms1024m -Xmx1024m"
call "%CATALINA_HOME%\bin\startup.bat"
```

如上，配置了JAVA_HOME，CATALINA_HOME，CATALINA_BASE，CATALINA_OPTS，JAVA_OPTS等参数，最后通过call命令调用外部tomcat容器启动tomcat。

- CATALINA_HOME：指向的是一个干净的官网下载的tomcat容器目录即可，无需其他配置。
- CATALINA_BASE：指向的目录是该案例所在的配置文件的根目录即可。
- CATALINA_OPTS：可以配置tomcat的一些公共的启动参数，比如开发环境中常用到的debug配置参数，如 `transport=dt_socket,server=y,suspend=n,address=7776`。

### 2.3 context.xml配置

每个WEB应用程序的初使化环境（InitialContext）可以配置于$CATALINA_HOME/conf/server.xml的<Context>节点中，也可以配置每个WEB应用程序环境(Context)于单独的XML文件中。

每个需要JNDI访问的context节点可以配置如下节点，

- Environment：为scalar environment实体配置名称及值，这些实体通过JNDI InitialContext开发给WEB应用程序(与在WEB应用布署描述文件(/WEB-INF/web.xml)中增加<env-entry>节点配置相同)。
- Resource：配置应用于WEB应用程序的资源名称及数据类型(与在WEB应用布署描述文件(/WEB-INF/web.xml)中增加<resource-ref>节点配置相同)。
- ResourceLink：为定义于全局JNDI环境中的资源增加链接，这些资源链访问定义于<Server>节点下的<GlobalNamingResources>的资源。
- Transaction：为在java:comp/UserTransaction中有效的初使化UserTransaction对象实例增加资源工厂。

本文配置一个简单的ResourceLink，然后通过server.xml中引用即可。

```xml
<ResourceLink name="jdbc/test" type="javax.sql.DataSource" global="jdbc/test"/>
```

其中name属性`jdbc/test`要与server.xml中Resources节点的name属性名称一致。

参考链接：https://tomcat.apache.org/tomcat-8.5-doc/jndi-resources-howto.html#context.xml_configuration

### 2.4 server.xml

#### 2.4.1 Resource配置

Tomcat为整个tomcat服务器维护了一个全局资源的命名空间，它们配置在conf/server.xml文件下的`GlobalNamingResources`节点中。你可以用`ResourcLink`节点引入每个web应用程序的上下文来暴露它们的资源在相应的web应用中。

> Tomcat maintains a separate namespace of global resources for the entire server. These are configured in the [`****`](https://tomcat.apache.org/tomcat-8.5-doc/config/globalresources.html) element of `$CATALINA_BASE/conf/server.xml`. You may expose these resources to web applications by using a [](https://tomcat.apache.org/tomcat-8.5-doc/config/context.html#Resource_Links) to include it in the per-web-application context.

本文配置如下：

```xml
<Resource
            name="jdbc/test"
            auth="Container"
            loginTimeout="10"
            maxWait="5000"
            maxAge="580000"
            type="javax.sql.DataSource"
            url="jdbc:mysql://localhost:3306/?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=GMT"
            driverClassName="com.mysql.cj.jdbc.Driver"
            username="root"
            password="landy8530"
    />
```

Resource标签属性：

- name：连接池名称，一般设定为jdbc/databasename


- auth：设定控制权为容器，固定


- type：数据类型，固定
- **factory**：数据源工厂，默认为"org.apache.commons.dbcp.BasicDataSourceFactory"

- maxTotal：最大活动连接数，在之前版本中是maxActive（当前数据源支持的最大并发数）


- maxIdle：最大空闲连接数（连接池中保留最大数目的闲置连接数）


- maxWaitMillis：最大空闲时间，在之前版本中是maxWait（当连接池中无连接时的最大等待毫秒数，在等当前设置时间过后还无连接则抛出异常）


- userName：访问数据库的用户名


- password：访问数据库的密码


- dirverClassName：驱动的全路径类名，MySQL6.0之后Driver名改为“com.mysql.cj.jdbc.Driver”，之前是“com.mysql.jdbc.Driver”


- url：指定数据库连接ip和数据库名称
- **validationQuery**：在返回应用之前，用于校验当前连接是否有效的SQL语句，如果指定了，当前查询语句至少要返回一条记录



URL属性的说明如下：

> 对于MySQL5.*及之前版本只需写`jdbc:mysql://127.0.0.1:3306/databaseName`就行，MySQL6.0及之后版本需要指定服务器时区属性，设定useSSL属性等，个属性之间用&连接，在xml/html文件中&用`&amp;`转义表示，应该写成：`jdbc:mysql://127.0.0.1:3306/databaseName?serverTimeZone=GMT%2B8&amp;useSSL=false`
>
> 若出现字符集问题则需添加下面两个参数：
>
> - useUnicode=true
>
> - characterEncoding=utf8
>
>
> Mysql时区异常信息如下：
>
> java.sql.SQLException: The server time zone value‘XXXXXX' is unrecognized or represents...

参考链接：https://tomcat.apache.org/tomcat-8.5-doc/jndi-resources-howto.html#Global_configuration

本文采用的是Mysql8.0，如何安装可以参考笔者之前的一篇文章[基于Windows 10安装Mysql 8.0.17](https://segmentfault.com/a/1190000020339589)。

#### 2.4.2 Engine配置

Engine配置比较简单，只要配置一个包含访问日志节点`Value`和引用上下文节点`Context`节点的`Host`节点即可。

```xml
<Host appBase="webapps" autoDeploy="true" name="localhost" unpackWARs="true">
    <!-- Access log processes all example.
                 Documentation at: /docs/config/valve.html
                 Note: The pattern used is equivalent to using pattern="common" -->
    <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs" pattern="%h %l %u %t &quot;%r&quot; %s %b" prefix="localhost_access_log" suffix=".log"/>
    <Context crossContext="true" docBase="C:\\03_code\\idea_workspace\\spring-boot-lesson\\spring-boot-1.x\\tomcat-jndi-demo\\target\\tomcat-jndi-demo" path="/jndi-demo"  reloadable="true"/>
</Host>
```

`Context`中的`docBase`属性指向真正的web应用的根目录，`path`为web应用的根路径或者叫做应用名称也可以。

### 2.5 web.xml配置

以下节点可以配置每个web应用中的web应用描述符（`/WEB-INF/web.xml`）定义资源。

- <env-entry>：环境实体（`Environment entry`），一个单值参数可以用于配置应用如何操作。
- <resource-ref>：资源引用（`Resource reference`）,典型的对象工厂为`JDBC DataSource`, `JavaMail Session`等，也可以自定义对象工厂作为资源引进tomcat中。
- <resource-env-ref>：环境资源引用（`Resource environment reference`）,在servlet2.4中引入的新的<resource-ref>的校验，这种可以简化配置，不需要校验信息。

本文配置如下：

```xml
<resource-ref>
    <res-ref-name>jdbc/test</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
</resource-ref>
```

参考链接：https://tomcat.apache.org/tomcat-8.5-doc/jndi-resources-howto.html#web.xml_configuration

## 3. Java读取JNDI配置

使用Java读取JNDI配置就很简单了，只要引用JNDI的`InitialContext`对象即可读取。主要逻辑如下：

```java
Context context = new InitialContext();
Context evnContext = (Context) context.lookup("java:comp/env");
dataSource = (DataSource) evnContext.lookup("jdbc/test");
```

得到了DataSource数据源就可以得到数据库连接对象了，

```java
Connection connection = dataSource.getConnection();
```

## 4. 演示

启动tomcat主要运行上文配置的`startup.bat`批处理文件即可，然后输入地址`http://localhost:8080/jndi-demo/jdbc/test`即可访问到本文的测试案例了。

![1568538853524](https://github.com/landy8530/spring-boot-lesson/blob/master/doc/spring-boot-1.x/jndi/1568538853524.png)

## 引用文献

1. https://www.blackhat.com/docs/us-16/materials/us-16-Munoz-A-Journey-From-JNDI-LDAP-Manipulation-To-RCE-wp.pdf
2. https://www.blackhat.com/docs/us-16/materials/us-16-Munoz-A-Journey-From-JNDI-LDAP-Manipulation-To-RCE.pdf
3. https://en.wikipedia.org/wiki/Java_Naming_and_Directory_Interface
4. https://tomcat.apache.org/tomcat-8.5-doc/jndi-resources-howto.html