package org.landy.springbootlesson4;

import org.landy.springbootlesson4.servlet.MyServletRequestListener;
import org.landy.springbootlesson4.spring.boot.MyFilter2;
import org.landy.springbootlesson4.spring.boot.MyServlet2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.servlet.DispatcherType;

@SpringBootApplication
@ServletComponentScan(basePackages = {"org.landy.springbootlesson4.servlet"})
public class SpringBootLesson4Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		//第一个参数是Sprintboot启动的来源类（source）
		SpringApplication.run(SpringBootLesson4Application.class, args);
	}

	@Bean
	public static ServletRegistrationBean servletRegistrationBean() {

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();

		servletRegistrationBean.setServlet(new MyServlet2());
		servletRegistrationBean.setName("my-servlet2");
		servletRegistrationBean.addUrlMappings("/spring-boot/myservlet2");
		servletRegistrationBean.addInitParameter("myname", "myvalue");

		return servletRegistrationBean;

	}

	@Bean
	public static FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

		filterRegistrationBean.setFilter(new MyFilter2());
		filterRegistrationBean.addServletNames("my-servlet2");

		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE);

		return filterRegistrationBean;

	}

	//这边再加上，则会执行两次，跟MyServletRequestListener上的@WebListener重复了
	@Bean
	public static ServletListenerRegistrationBean servletListenerRegistrationBean() {
		ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
		servletListenerRegistrationBean.setListener(new MyServletRequestListener());
		return servletListenerRegistrationBean;
	}

	//JSP on Spring Boot 组装器
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.sources(SpringBootLesson4Application.class);
		return builder;
	}
}
