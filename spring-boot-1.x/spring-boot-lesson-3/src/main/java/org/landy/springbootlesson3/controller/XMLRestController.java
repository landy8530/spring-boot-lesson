package org.landy.springbootlesson3.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XMLRestController {

    @GetMapping(path = "/xml/user",
            produces = MediaType.APPLICATION_XML_VALUE)
    public User user() {

        User user = new User();

        user.setName("XML");
        user.setAge(30);

        return user;
    }

    //此方法如果引入了jackson-dataformat-xml包则默认会优先使用xml的格式输出。
//    @GetMapping(path = "/json/user/xml")
//    public User user4xml() {
//        User user = new User();
//
//        user.setName("XML");
//        user.setAge(30);
//
//        return user;
//    }

}
