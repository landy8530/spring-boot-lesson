package org.landy.springbootjmx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.landy.springbootjmx")
public class SpringBootJmxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJmxApplication.class, args);
	}

}

