package com.lzz.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzz on 2018/3/18.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index(Model model) {
        String page = "index";
        return page;
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        String page = "welcome";
        return page;
    }
}
