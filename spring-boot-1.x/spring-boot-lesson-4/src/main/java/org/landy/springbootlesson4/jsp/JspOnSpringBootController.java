package org.landy.springbootlesson4.jsp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JspOnSpringBootController {

    // 从 application.yml 中读取配置，如取不到默认值为Hello Jsp
    @Value("${application.hello:Hello Jsp}")
    private String hello = "Hello Jsp";

    /**
     * 默认页<br/>
     * @RequestMapping("/") 和 @RequestMapping 是有区别的
     * 如果不写参数，则为全局默认页，加入输入404页面，也会自动访问到这个页面。
     * 如果加了参数“/”，则只认为是根页面。
     * 可以通过localhost:7070或者localhost:7070/index访问该方法
     */
    @RequestMapping(value = {"/","/index"})
    public String index(Model model) {
        // 直接返回字符串，框架默认会去 spring.view.prefix 目录下的 （index拼接spring.view.suffix）页面
        // 本例为 /WEB-INF/jsp/index.jsp
        model.addAttribute("name", "Landy");
        model.addAttribute("hello", hello);

        return "index";
    }

}
