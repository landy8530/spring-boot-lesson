package org.landy.springbootlesson1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class SpringBootLesson1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLesson1Application.class, args);
	}
}
