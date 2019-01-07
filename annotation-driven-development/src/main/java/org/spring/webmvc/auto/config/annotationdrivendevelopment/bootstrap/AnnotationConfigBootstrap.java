package org.spring.webmvc.auto.config.annotationdrivendevelopment.bootstrap;

import org.spring.webmvc.auto.config.annotationdrivendevelopment.config.UserConfiguration;
import org.spring.webmvc.auto.config.annotationdrivendevelopment.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Annotation装配
 * Created by Landy on 2019/1/7.
 */
public class AnnotationConfigBootstrap {

    public static void main(String[] args) {
        // 构建一个 Spring Application 上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();

        // 需要注册一个UserConfiguration 的Bean
        applicationContext.register(UserConfiguration.class);

        applicationContext.refresh();

        User user = applicationContext.getBean("user", User.class);

        System.out.printf("user.getName() = %s \n",user.getName());



    }

}
