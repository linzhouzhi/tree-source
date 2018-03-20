package com.lzz.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzz on 2018/3/20.
 */
@Controller
@RequestMapping("/redis")
public class RedisControl {
    @RequestMapping("/content")
    public String content(Model model) {
        return "redis/content";
    }
}
