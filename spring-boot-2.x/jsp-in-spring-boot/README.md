# JSP在Spring Boot中的应用

## 1. Spring Boot应用（自动装配）

Spring Boot帮我们实现了以下三部分的逻辑

### 1.1 DispatcherServlet

### 1.2 InternalResourceViewResolver

### 1.3 @Controller

## 2. Spring Boot整合JSP

虽然Spring Boot默认已经不支持JSP了，但是本着学习至上的原则，在多方查资料的情况下，进行了一番整合操作。

### 2.1 Maven依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JSP 渲染引擎 -->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <!-- 如果是外部tomcat部署的话，provided属性需要开启 -->
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
    </dependency>

    <!-- JSTL -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### 2.2 Controller代码

```java
@Controller
public class JspOnSpringBootController {

    // 从 application.yml 中读取配置，如取不到默认值为Hello Jsp
    @Value("${application.hello:Hello Jsp}")
    private String hello = "Hello Jsp";

    /**
     * 默认页<br/>
     * @RequestMapping("/") 和 @RequestMapping 是有区别的
     * 如果不写参数，则为全局默认页，加入输入404页面，也会自动访问到这个页面。
     * 如果加了参数“/”，则只认为是根页面。
     * 可以通过localhost:7070或者localhost:7070/index访问该方法
     */
    @RequestMapping(value = {"/","/index"})
    public String index(Model model) {
        // 直接返回字符串，框架默认会去 spring.view.prefix 目录下的 （index拼接spring.view.suffix）页面
        // 本例为 /WEB-INF/jsp/index.jsp
        model.addAttribute("name", "Landy");
        model.addAttribute("hello", hello);

        return "index";
    }

}
```

### 2.3 application.properties配置

```properties
#service端口（Dev）
server.port=7070

spring.mvc.view.prefix = /WEB-INF/jsp/
spring.mvc.view.suffix = .jsp

application.hello = hello jsp on spring boot
```

### 2.4 Jsp页面代码

```jsp
<html>
<head>
    <title>JSP on Spring Boot</title>
</head>
<body>
    <h1 style="color: red">${name}, ${hello}</h1>

</body>
</html>
```

### 2.5 运行

按理说，以上完成后就可以运行顺利看到页面结果了，但是如果直接运行SpringApplication的main方法是不能看到结果的，报404.

![1567522145977](https://github.com/landy8530/spring-boot-lesson/doc/spring-boot-1.x/jsp-on-spring-boot/1567522145977.png)

经过网上一番寻找发现其根本原因，可以参见文章[为什么整合jsp后必须通过spring-boot:run方式启动]( https://segmentfault.com/a/1190000009785247)查看具体原因。

以下三种方式均可正常运行。

#### 2.5.1 spring-boot:run 启动

使用命令 `maven clean spring-boot:run`或者使用idea的maven插件运行均可。

![1567522493031](https://github.com/landy8530/spring-boot-lesson/doc/spring-boot-1.x/jsp-on-spring-boot/1567522493031.png)

访问地址：http://localhost:7070/index

#### 2.5.2 打成war包

1. 设置pom.xml文件packaging属性应该为war。

   ```xml
   <groupId>org.landy</groupId>
   <artifactId>spring-boot-lesson-4</artifactId>
   <version>0.0.1-SNAPSHOT</version>
   <packaging>war</packaging>
   ```

   

2. 执行打包命令 `maven clean package`

3. 执行运行war包命令 `java -jar war包名称`

   ![1567522902523](https://github.com/landy8530/spring-boot-lesson/doc/spring-boot-1.x/jsp-on-spring-boot/1567522902523.png)

![1567522923421](https://github.com/landy8530/spring-boot-lesson/doc/spring-boot-1.x/jsp-on-spring-boot/1567522923421.png)

4. 访问地址：http://localhost:7070/index

#### 2.5.3 外部tomcat部署方法

1. 确保<scope>provided</scope> 没有被注释。

   > Spring Boot内嵌的Servlet API运行时由tomcat提供，需要在运行时剔除。

2. 修改 `SpringBootLesson4Application`类，需要继承 `org.springframework.boot.web.support.SpringBootServletInitializer`,然后实现其configure方法，具体内容为return builder.sources(本类类名.class);（注意，这里不能是this，this是一个类的实例，而xxx.class是类的模板）。

   > 外部容器部署的话，就不能依赖于Application的main方法启动了，而是要依赖于类似servlet的web.xml的方式来启动Spring的应用上下文了。此时需要继承SpringBootServletInitializer并实现configure方法。

3. 打包：`maven clean package`

4. 放在tomcat下的webapp目录,重命名即可。

   ![1567523474393](https://github.com/landy8530/spring-boot-lesson/doc/spring-boot-1.x/jsp-on-spring-boot/1567523474393.png)

5. 访问地址： http://localhost:8080/jsp-in-spring-boot 

   以上三种方式都可以成功访问到以下页面。

   ![1567523590450](https://github.com/landy8530/spring-boot-lesson/doc/spring-boot-1.x/jsp-on-spring-boot/1567523590450.png)

### 2.6 问题说明

在外部tomcat部署的时候，必须继承 `SpringBootServletInitializer`类，如果没有继承则会报404错误，如下页面所示，

![1567524075833](https://github.com/landy8530/spring-boot-lesson/doc/spring-boot-1.x/jsp-on-spring-boot/1567524075833.png)



本文事例代码共有两处，

- 未继承SpringBootServletInitializer类的工程参见地址：https://github.com/landy8530/spring-boot-lesson/tree/master/spring-boot-2.x/jsp-in-spring-boot
- 继承了SpringBootServletInitializer类的工程参见地址：https://github.com/landy8530/spring-boot-lesson/tree/master/spring-boot-1.x/spring-boot-lesson-4