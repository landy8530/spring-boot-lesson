package org.landy.springbootlesson1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller //表明当前类是一个Controller
@SpringBootApplication
public class SpringBootLesson1Application {

	public static void main(String[] args) {
		//第一个参数是Sprintboot启动的来源类（source）
		SpringApplication.run(SpringBootLesson1Application.class, args);
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/rest")
	@ResponseBody
	public Map<String, Object> rest() {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("1", "A");
		data.put("2", 2);

		return data;
	}

}
