package com.zt.blog.controller;

import com.zt.blog.bean.Blog;
import com.zt.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller
 * @since 2023-06-27 9:02
 */
@Controller
@RequestMapping("/archives")
public class ShowArchivesController {
    @Autowired
    private BlogService blogService;

    @GetMapping()
    public String archives(Model model){
        //查询博客数目
        int blogCnt = blogService.count();
        model.addAttribute("blogCnt", blogCnt);
        //查询所有年份
        List<String> yearList = blogService.getAllYear();
        model.addAttribute("years", yearList);
        //查询修改年份下的博客列表
        Map<String, List<Blog>> blogMap = new HashMap<>();
        for(String year : yearList){
            blogMap.put(year, blogService.listBlogByYear(year));
        }

        model.addAttribute("blogMap", blogMap);
        return "archives";
    }
}
