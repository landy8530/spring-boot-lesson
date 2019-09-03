package org.landy.jspinspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "org.landy.jspinspringboot.web.controller")
public class JspInSpringBootApplication /*extends SpringBootServletInitializer*/ {

	public static void main(String[] args) {
		SpringApplication.run(JspInSpringBootApplication.class, args);
	}

	/*//JSP on Spring Boot 组装器
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.sources(JspInSpringBootApplication.class);
		return builder;
	}*/
}
