package com.zt.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.blog.bean.Blog;
import com.zt.blog.bean.Type;
import com.zt.blog.constant.Constants;
import com.zt.blog.controller.vo.BlogVO;
import com.zt.blog.controller.vo.TypeVO;
import com.zt.blog.service.BlogService;
import com.zt.blog.service.TypeService;
import com.zt.blog.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller
 * @since 2023-06-26 18:57
 */
@Controller
@RequestMapping("/type")
public class ShowTypeController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    //类型对应博客分页
    @GetMapping("/{typeId}")
    public String type(@PathVariable(required = false) Integer typeId,
                       @RequestParam(defaultValue = "1") Integer pageNo,
                       @RequestParam(defaultValue = Constants.DISPLAY_PAGE_SIZE) Integer pageSize,
                       Model model){
        //查类型
        List<Type> types = typeService.list();
        List<TypeVO> typeVOList = types.stream().map(type -> {
            TypeVO typeVO = new TypeVO();
            typeVO.setId(type.getId());
            typeVO.setName(type.getName());
            //该类型下博客数
            LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Blog::getTypeId, type.getId());
            typeVO.setBlogCnt(blogService.count(wrapper));
            return typeVO;
        }).sorted((t1, t2) -> {
            return t2.getBlogCnt() - t1.getBlogCnt();
        }).collect(Collectors.toList());
        model.addAttribute(Constants.TYPES_KEY, typeVOList);
        //查类型对应的博客
        if(typeId == -1){
            //取第一个类型id
            typeId = typeVOList.get(0).getId();
        }
        model.addAttribute("typeId", typeId);
        Page<Blog> page = blogService.pageListByTypeId(typeId, pageNo, pageSize);
        //处理日期 查类型名称
        Page<BlogVO> blogVOPage = new Page<>();
        BeanUtils.copyProperties(page, blogVOPage);
        List<BlogVO> list = page.getRecords().stream().map(blog -> {
            BlogVO blogVO = BlogVO.convert(blog);
            //查类型名称
            Type type = typeService.getById(blog.getTypeId());
            blogVO.setType(type.getName());
            //查作者
            blogVO.setUser(userService.getById(blog.getUserId()));
            return blogVO;
        }).collect(Collectors.toList());
        blogVOPage.setRecords(list);
        model.addAttribute(Constants.PAGE_KEY, blogVOPage);
        return "types";
    }
}
