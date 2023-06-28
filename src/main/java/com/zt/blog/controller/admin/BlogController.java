package com.zt.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.blog.bean.*;
import com.zt.blog.constant.Constants;
import com.zt.blog.controller.vo.BlogVO;
import com.zt.blog.service.BlogService;
import com.zt.blog.service.BlogTagService;
import com.zt.blog.service.TagService;
import com.zt.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller.admin
 * @since 2023 - 04 - 22 - 10:18
 */
@Controller
@RequestMapping("/admin/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagService blogTagService;


    /**
     * 博客列表页面
     * @param pageNo 页码
     * @param pageSize 每页显示条数
     */
    @GetMapping
    public String list(@RequestParam(defaultValue = "1") Integer pageNo,
                       @RequestParam(defaultValue = Constants.DISPLAY_PAGE_SIZE) Integer pageSize,
                       Model model){
        //查分类
        List<Type> types = typeService.list();
        model.addAttribute(Constants.TYPES_KEY, types);
        //查询博客列表
        Page<Blog> page = blogService.pageList(pageNo, pageSize, null, null, null);
        //处理日期 查类型名称
        Page<BlogVO> blogVOPage = new Page<>();
        BeanUtils.copyProperties(page, blogVOPage);
        List<BlogVO> list = page.getRecords().stream().map(blog -> {
            BlogVO blogVO = BlogVO.convert(blog);
            //查类型名称
            Type type = typeService.getById(blog.getTypeId());
            blogVO.setType(type.getName());
            return blogVO;
        }).collect(Collectors.toList());
        blogVOPage.setRecords(list);

        model.addAttribute(Constants.PAGE_KEY, blogVOPage);
        return "/admin/blogs";
    }

    /**
     * 搜索博客管理列表
     * 博客列表页面点击按钮调用，局部更新表格
     * @param pageNo 页码
     * @param pageSize 每页显示条数
     * @param title 标题
     * @param typeId 所属类型id
     * @param recommend 是否推荐
     */
    @PostMapping("/search")
    public Object search(@RequestParam(defaultValue = "1") Integer pageNo
            ,@RequestParam(defaultValue = Constants.DISPLAY_PAGE_SIZE) Integer pageSize
            ,@RequestParam(defaultValue = "") String title
            ,@RequestParam(required = false) Integer typeId
            ,@RequestParam(required = false)Boolean recommend,Model model){
        Page<Blog> page = blogService.pageList(pageNo, pageSize, title, typeId, recommend);
//        model.addAttribute(Constants.PAGE_KEY, page);
        //处理日期 查类型名称
        Page<BlogVO> blogVOPage = new Page<>();
        BeanUtils.copyProperties(page, blogVOPage);
        List<BlogVO> list = page.getRecords().stream().map(blog -> {
            BlogVO blogVO = BlogVO.convert(blog);
            //查类型名称
            Type type = typeService.getById(blog.getTypeId());
            blogVO.setType(type.getName());
            return blogVO;
        }).collect(Collectors.toList());
        blogVOPage.setRecords(list);
        model.addAttribute(Constants.PAGE_KEY, blogVOPage);
        //返回表格片段
        return "/admin/blogs :: blogList";
    }

    /**
     * 查类型和标签
     */
    private void setTypeAndTag(Model model){
        //查类型
        List<Type> types = typeService.list();
        //查标签
        List<Tag> tags = tagService.list();
        model.addAttribute(Constants.TYPES_KEY, types);
        model.addAttribute(Constants.TAGS_KEY, tags);
    }
    /**
     * 跳转到博客新增页面
     */
    @GetMapping("/toSave.do")
    public String toSave(Model model){
        setTypeAndTag(model);
        return "/admin/blogs-input";
    }

    /**
     * 保存博客
     * @param blog 博客信息
     * @param session 获取用户id
     * @param tagIds 标签
     */
    @PostMapping("/save")
    @Transactional
    public String save(Blog blog, HttpSession session, Integer[] tagIds, RedirectAttributes attributes){
        //设置当期用户id
        User user = (User)session.getAttribute(Constants.SESSION_USER_KEY);
        blog.setUserId(user.getId());

        blog.setViewCnt(0);
        blog.setUpdateTime(new Date());
        blog.setCreateTime(new Date());
        //保存博客
        boolean success = blogService.save(blog);
        if(success){
            if(tagIds != null && tagIds.length > 0){
                //保存博客和标签的关联
                for(Integer tagId : tagIds){
                    BlogTag blogTag = new BlogTag();
                    blogTag.setBlogId(blog.getId());
                    blogTag.setTagId(tagId);
                    blogTagService.save(blogTag);
                }
            }
            attributes.addFlashAttribute("message", "操作成功!");
        }else{
            attributes.addFlashAttribute("message", "操作失败!");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 删除博客
     * @param id 博客ID
     */
    @GetMapping("/delete/{id}")
    @Transactional
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        //检查对应id的博客是否存在
        Blog blog = blogService.getById(id);
        if(blog == null){
            attributes.addFlashAttribute(Constants.MESSAGE_KEY, "博客不存在!");
            return  "redirect:/admin/blogs";
        }
        //删除博客和标签的关联关系
        blogTagService.deleteByBlogId(id);
        //删除博客
        if(!blogService.removeById(id)){
            attributes.addFlashAttribute(Constants.MESSAGE_KEY, "删除博客失败!");
        }else{
            attributes.addFlashAttribute(Constants.MESSAGE_KEY, "删除博客成功!");
        }
        return  "redirect:/admin/blogs";
    }

    /**
     * 跳转到修改页面
     * @param id 博客ID
     * @return
     */
    @GetMapping("/toEdit.do/{id}")
    public String toEdit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        //检查对应id的博客是否存在
        Blog blog = blogService.getById(id);
        if(blog == null){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "博客不存在！");
            return "redirect:/admin/blogs";
        }
        BlogVO blogVO = BlogVO.convert(blog);
        //查标签
        String tagIds = blogTagService.getTagIdsByBlogId(id);
        blogVO.setTagIds(tagIds);
        setTypeAndTag(model);
        //保持到域中
        model.addAttribute(Constants.BLOG_KEY, blogVO);
        return  "/admin/blogs-input";
    }

    /**
     * 修改博客
     * @param blog 收集前端传来的数据
     */
    @PostMapping("/update")
    public String update(Blog blog, RedirectAttributes attributes, Integer[] tagIds){
        //前端没有传过来的值保持不变
        Blog b = blogService.getById(blog.getId());
        BeanUtils.copyProperties(blog, b, "viewCnt");//MyBeanUtils.getNullPropertyNames(blog)
        //四个复选按钮 勾选是true， 否则为null
        b.setRecommend(blog.getRecommend() != null);//推荐
        b.setAppreciation(blog.getAppreciation() != null);//赞赏
        b.setReviewable(blog.getReviewable() != null); //评论
        b.setShareStatement(blog.getShareStatement() != null); //转载
        b.setUpdateTime(new Date());
        blogService.updateById(b);

        //修改博客和标签的关联
        blogTagService.deleteByBlogId(b.getId());
        for(Integer tagId : tagIds){
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(b.getId());
            blogTag.setTagId(tagId);
            blogTagService.save(blogTag);
        }
        attributes.addFlashAttribute(Constants.MESSAGE_KEY, "修改成功!");
        return "redirect:/admin/blogs";
    }
}
