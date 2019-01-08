# Java Beans内省机制以及在Spring中的应用

## 1. 企业级Beans

EJB就像共产主义，听说过，没见过

EJB3.1以后是嵌入式的

- RPC
  - 有状态Bean和无状态Bean
- JPA
  - 持久化Bean
- JMS
  - 消息Bean

## 2. Java Beans内省机制

### 2.1 Bean基础

- 充血模型和贫血模型
  - 充血模型：包括了一些状态和具体的业务操作
  - 贫血模型：只有setter和getter
- 状态Bean
  - Session Bean
  - JSF：除了页面上的操作，还需要管理Session（Session Bean）
  - 状态Bean：比如有个远程服务

### 2.2 Java反射

获取Class信息

- 构造器（Constructor）
- 方法（Method）
- 字段（Field）

### 2.3 内省

获取Java BeanInfo，Bean的描述针对的是某个类的

类（模版）没有状态，实例（Bean）是有状态的

- Bean描述符（BeanDescriptor）：不只是Bean的描述，

- 方法描述符（MethodDescriptor）
- 字段描述符（FieldDecriptor）

Java MethodDescriptor实现：

```java
public class MethodDescriptor extends FeatureDescriptor {

    private final MethodRef methodRef = new MethodRef();
    ....
}    
```

```java
final class MethodRef {
    private String signature;
    private SoftReference<Method> methodRef;
    private WeakReference<Class<?>> typeRef;
    .....
}        
```

上述代码说明：

BeanInfo 对象不是单例，是多例

Class很多时候是单例的

弱引用：当作缓存的时候，不用的时候可以马上让GC收回

软引用：



setter和getter并不是需要成对出现

Java PropertyDescriptor实现

```java
public class PropertyDescriptor extends FeatureDescriptor {

    private Reference<? extends Class<?>> propertyTypeRef;
    private final MethodRef readMethodRef = new MethodRef();
    private final MethodRef writeMethodRef = new MethodRef();
    private Reference<? extends Class<?>> propertyEditorClassRef;
    .....
}        
```

readMethodRef和writeMethodRef可以任意为空，但是不会同时为空，因为判断一个property的时候，我们必须根据setter和getter来判断。

## 3. Java Beans事件监听

### 3.1 属性变化监听器（PropertyChangeListener）

### 3.2 属性变化事件（PropertyChangeEvent）

- 事件源（Source）

- 属性名称（PropertyName）

- 变化前值（OldValue）

- 变化后值（NewValue）

## 4. Spring Beans属性处理

- 属性修改器（PropertyEditor）
- 属性修改器注册（PropertyEditorRegistry）
- PropertyEditor注册器（PropertyEditorRegistrar）
- 自定义PropertyEditor配置器（CustomEditorConfigurer）

## 5. 内省机制在Spring中的应用
