package com.zt.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.blog.bean.Blog;
import com.zt.blog.bean.BlogTag;
import com.zt.blog.bean.Tag;
import com.zt.blog.bean.Type;
import com.zt.blog.constant.Constants;
import com.zt.blog.controller.vo.BlogVO;
import com.zt.blog.controller.vo.TagVO;
import com.zt.blog.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller
 * @since 2023-06-26 20:01
 */
@Controller
@RequestMapping("/tag")
public class ShowTagsController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogTagService blogTagService;
    @RequestMapping("/{tagId}")
    public String tag(@PathVariable(required = false) Integer tagId,
                      @RequestParam(defaultValue = "1") Integer pageNo,
                      @RequestParam(defaultValue = Constants.DISPLAY_PAGE_SIZE) Integer pageSize,
                      Model model){
        //查标签
        List<Tag> tags = tagService.list();
        List<TagVO> tagVOList = tags.stream().map(tag -> {
            TagVO tagVo = new TagVO();
            BeanUtils.copyProperties(tag, tagVo);
            //对应博客数量
            LambdaQueryWrapper<BlogTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(BlogTag::getTagId, tag.getId());
            tagVo.setBlogCnt(blogTagService.count(wrapper));
            return tagVo;
        }).sorted((t1, t2) -> t2.getBlogCnt() - t1.getBlogCnt()).collect(Collectors.toList());
        if(tagId == -1){
            tagId = tagVOList.get(0).getId();
        }
        model.addAttribute("tagId", tagId);
        model.addAttribute(Constants.TAGS_KEY, tagVOList);
        //博客列表
        List<Integer> blogIds = blogTagService.listBlogIdsByTagId(tagId);
        Page<Blog> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Blog::getId, blogIds);
        Page<Blog> blogPage = blogService.page(page, wrapper);
        //do -> vo
        Page<BlogVO> blogVOPage = new Page<>();
        BeanUtils.copyProperties(blogPage, blogVOPage);
        List<BlogVO> list = blogPage.getRecords().stream().map(blog -> {
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
        return "tags";
    }
}
