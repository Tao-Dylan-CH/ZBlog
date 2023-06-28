package com.zt.blog.controller.admin;

import com.zt.blog.bean.User;
import com.zt.blog.constant.Constants;
import com.zt.blog.service.UserService;
import com.zt.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller
 * @since 2023 - 04 - 20 - 21:00
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping()
    public String loginPage(){
        return "/admin/login";
    }

    /**
     * 后台用户登录
     * @param username 用户名
     * @param password 密码（明文）
     */
    @PostMapping("/login")
    public String login(String username, String password,
                        HttpSession session, RedirectAttributes attributes){
        //md5加密
        String md5 = MD5Utils.code(password);
        //登录验证
        User user = userService.checkUser(username, md5);
        if(user != null){
            user.setPassword(null);
            session.setAttribute(Constants.SESSION_USER_KEY, user);
            return "/admin/index";
        }
        attributes.addFlashAttribute("message", "用户名或密码错误");
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(Constants.SESSION_USER_KEY);
        return "redirect:/admin";
    }
}
