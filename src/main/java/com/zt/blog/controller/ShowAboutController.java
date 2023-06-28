package com.zt.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller
 * @since 2023-06-27 9:30
 */
@Controller
@RequestMapping("/about")
public class ShowAboutController {
    //关于我
    @GetMapping()
    public String about(){
        return "about";
    }
}
