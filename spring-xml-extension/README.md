# Spring自定义XML配置扩展

## 1. Spring XML配置扩展机制

### 1.1 XML技术简介

- DOM --> Document Object Model（Tree技术）
  - 属性结构，好理解，性能最差
  - DOM技术在Java中的以 `org.w3c.dom.Document` 实现。

- SAX -> Simple API for XML（Event技术）
  - 文本处理，性能好
  - Spring5中的Marshalling技术就是利用SAX Handler实现

- XML Stream -> BEA 公司（Stream技术）
  - 时间处理，性能良好，相对好理解
  - `java.util.stream.Stream` 

- JAXB -> Java API XML Binding （Spring2.0后采用的方式）
  - 面向对象，性能良好，好理解

### 1.2 Document技术



## 2. Spring Framework内建实现

### 2.1 Spring版本对应处理方式

Spring版本对应相应的特性，Spring模块对应相应的功能

- Spring2.0以下，利用的是DTD方式（约束较小）
  - log4j.dtd
  - spring.dtd

- Spring2.0以上，利用的是Schema方式（约束较大）
  - Spring-beans.xsd
  - Spring-context.xsd

Schema -> Java API XML Binding（JAXB）技术

SOAP(Simple Object Access protocol) -> WSDL

### 2.2 Spring 配置placeholder的两种方式

#### 2.2.1 利用bean标签配置

```xml
<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer" >
        <property name="locations">
            <value>application.properties</value>
        </property>
        <property name="fileEncoding" value="UTF-8" />
    </bean>
```

#### 2.2.2 利用<context:property-placeholder> 标签

```xml
<context:property-placeholder location="classpath:application.properties" file-encoding="UTF-8" />
```

#### 2.2.3 两种方式优劣性

经过查看源码得知，上面两种方式的父类均为 `org.springframework.beans.factory.config.PlaceholderConfigurerSupport`，他们的子类分别为：

- `org.springframework.beans.factory.config.PropertyPlaceholderConfigurer`
- `org.springframework.context.support.PropertySourcesPlaceholderConfigurer`

`PropertySourcesPlaceholderConfigurer`是在spring-context.xsd文件中查到，如下所示：

```x&#39;s
<xsd:appinfo>
    <tool:annotation>
    <tool:exports 		type="org.springframework.context.support.PropertySourcesPlaceholderConfigurer"/>
    </tool:annotation>
</xsd:appinfo>
```

通过XML Schema的扩展可以替代Bean配置，它有以下优点：

- 配置简化
- 不需要配置bean中对应的class类名（不会检查类名是否正确，而XML schema是强检查的，属性类型未填就会报错）

### 2.3 Schema配置

约定配置文件位于META-INF/spring.schemas，存储内容格式：key=绝对路径，value=相对路径

Properties文件格式：key=value,key:value

如下所示：

```
http\://www.springframework.org/schema/context/spring-context.xsd=org/springframework/context/config/spring-context-4.3.xsd
```

注意：上面\表示转义，因为在properties文件中，Properties文件格式有两种表达方式：key=value,key:value。

Schema 绝对路径：

```
http://www.springframework.org/schema/context/spring-context.xsd
```

Schema 相对路径：

```
org/springframework/context/config/spring-context.xsd
```

然后在spring-context.xsd中定义如下命名空间（利用Schema的相对路径）：

```
targetNamespace="http://www.springframework.org/schema/context"
```

### 2.4 Namespace Handler配置

问题：schema 定义namespace -> 处理类是谁？

约定配置文件位于META-INF/spring.handlers，存储内容格式：key=Schema 命名空间，value=Handler类

如下所示：

```
http\://www.springframework.org/schema/context=org.springframework.context.config.ContextNamespaceHandler
```

key值就是Schema的命名空间：

```
http://www.springframework.org/schema/context
```

对应的Handler类：

```
org.springframework.context.config.ContextNamespaceHandler
```

### 2.5 Bean解析

通过查看如下`org.springframework.context.config.ContextNamespaceHandler`源码可知，spring-context.xml文件中 `<context:property-placeholder location="classpath:application.properties" file-encoding="UTF-8" />` 的Locale Element name 映射 `BeanDefinitionParser`，比如"property-placeholder" 映射`PropertyPlaceholderBeanDefinitionParser`。

```java
public class ContextNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("property-placeholder", new PropertyPlaceholderBeanDefinitionParser());
		.....
	}
}
```

由此可知，我们Bean解析相关涉及两个概念：

- Bean定义：`org.springframework.beans.factory.config.BeanDefinition`
- Bean定义解析器：`org.springframework.beans.factory.xml.BeanDefinitionParser`



## 3. 自定义XML配置扩展

