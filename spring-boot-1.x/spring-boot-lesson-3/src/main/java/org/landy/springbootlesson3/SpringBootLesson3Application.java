package org.landy.springbootlesson3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootLesson3Application {

	public static void main(String[] args) {
		//第一个参数是Sprintboot启动的来源类（source）
		SpringApplication.run(SpringBootLesson3Application.class, args);
	}

}
