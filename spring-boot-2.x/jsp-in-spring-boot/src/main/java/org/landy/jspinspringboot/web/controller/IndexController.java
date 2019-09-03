package org.landy.jspinspringboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Index Controller(Application Controller)
 */
@Controller
@RequestMapping("/home")
public class IndexController {

    //http://localhost:8088/home/index?message=landy
    @RequestMapping("/index")
    public String index(@RequestParam(required = false) String message,
                        Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
