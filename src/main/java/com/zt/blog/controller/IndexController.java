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
import com.zt.blog.controller.vo.TypeVO;
import com.zt.blog.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller
 * @since 2023 - 04 - 20 - 14:42
 */
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private UserService userService;

    //首页
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") Integer pageNo
            , @RequestParam(defaultValue = Constants.DISPLAY_PAGE_SIZE) Integer pageSize
            , Model model) {
        Page<Blog> page = blogService.pageOrderByUpdateTime(pageNo, pageSize);
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
        //博客分页
        model.addAttribute(Constants.PAGE_KEY, blogVOPage);
        //类型
        List<Type> typeList = typeService.listTop(Constants.SHOW_TYPE_SIZE);
        List<TypeVO> typeVOList = typeList.stream().map(type -> {
            TypeVO typeVO = new TypeVO();
            typeVO.setId(type.getId());
            typeVO.setName(type.getName());
            //该类型下博客数
            LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Blog::getTypeId, type.getId());
            typeVO.setBlogCnt(blogService.count(wrapper));
            return typeVO;
        }).sorted((t1, t2) -> t2.getBlogCnt() - t1.getBlogCnt()).collect(Collectors.toList());
        model.addAttribute(Constants.TYPES_KEY, typeVOList);
        //标签
        List<Tag> tagList = tagService.listTop(Constants.SHOW_TAG_SIZE);
        List<TagVO> tagVOList = tagList.stream().map(tag -> {
            TagVO tagVO = new TagVO();
            tagVO.setId(tag.getId());
            tagVO.setName(tag.getName());
            //该标签下博客数
            LambdaQueryWrapper<BlogTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(BlogTag::getTagId, tag.getId());
            tagVO.setBlogCnt(blogTagService.count(wrapper));
            return tagVO;
        }).sorted((t1, t2) -> t2.getBlogCnt() - t1.getBlogCnt()).collect(Collectors.toList());
        model.addAttribute(Constants.TAGS_KEY, tagVOList);
        //推荐博客
        model.addAttribute(Constants.RECOMMEND_BLOG, blogService.listRecommendBlogTop(Constants.RECOMMEND_BLOG_SIZE));
        return "index";
    }

    //全局搜索
    @PostMapping("/search")
    public String search(String query,
                         @RequestParam(defaultValue = "8") Integer pageSize,
                         @RequestParam(defaultValue = "1") Integer pageNo,
                         Model model){
        //根据标题 或 描述 模糊查询
        Page<Blog> blogPage = blogService.pageLikeByTitleOrDescription(pageNo, pageSize, query);
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
        model.addAttribute("query", query);
        return "search";
    }
    //博客详情
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Integer id, Model model){
        Blog blog = blogService.getAndConvert(id);
        BlogVO blogVO = BlogVO.convert(blog);
        //查类型
        Type type = typeService.getById(blog.getTypeId());
        blogVO.setType(type.getName());
        //查标签
        List<Tag> tags = tagService.listTags(blogTagService.listTagIdsByBlogId(id));
        blogVO.setTags(tags);
        //查作者
        blogVO.setUser(userService.getById(blog.getUserId()));
        model.addAttribute(Constants.BLOG_KEY, blogVO);
        //viewCnt + 1
        blogService.increasePageView(blog);
        return "blog";
    }
    //页脚 最新博客
    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        List<Blog> newBlogs = blogService.listRecommendBlogTop(3);
        model.addAttribute("newBlogs", newBlogs);
        return "_fregments :: newBlogList";
    }
}
