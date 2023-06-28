package com.zt.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.blog.bean.Tag;
import com.zt.blog.constant.Constants;
import com.zt.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec 标签管理
 * @since 2023 - 04 - 21 - 17:08
 */
@Controller
@RequestMapping("/admin/tags")
public class TagsController {
    public static final String KEY = "tag";
    @Autowired
    private TagService tagService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 每页显示条数
     * @param name 标签名
     * @return
     */
    @GetMapping
    public String list(@RequestParam(defaultValue = "1") Integer pageNo,
                       @RequestParam(defaultValue = Constants.DISPLAY_PAGE_SIZE) Integer pageSize,
                       @RequestParam(defaultValue = "") String name, Model model){
        Page<Tag> page = tagService.pageFuzzyByName(pageNo, pageSize, name);
        model.addAttribute(Constants.PAGE_KEY, page);
        return "/admin/tags";
    }
    /**
     * 跳转到新增页面
     */
    @GetMapping("/toSave.do")
    public String toSave(){
        return "/admin/tags-input";
    }

    /**
     * 新增标签
     * @param name 标签名称
     * @return
     */
    @PostMapping("/save")
    public String save(String name, RedirectAttributes redirectAttributes){
        Tag tag = new Tag();
        tag.setName(name);
        //检测重复
        if(tagService.getByName(name) != null){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "标签名称已存在");
            //用于回显
            redirectAttributes.addFlashAttribute(Constants.TAG_KEY, tag);
            return "redirect:/admin/tags/toSave.do";
        }
        boolean success = tagService.save(tag);
        if(success){
            redirectAttributes.addAttribute(Constants.MESSAGE_KEY, "添加成功");
        }else{
            redirectAttributes.addAttribute(Constants.MESSAGE_KEY, "添加失败");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 跳转到修改页面
     * @param id 标签id
     * @return
     */
    @GetMapping("/toEdit.do/{id}")
    public String toEdit(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Tag tag = tagService.getById(id);
        if(tag == null){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "标签名称不存在");
            return "redirect:/admin/tags";
        }
        request.setAttribute(Constants.TAG_KEY, tag);
        return "/admin/tags-input";
    }

    /**
     * 修改标签
     * @param id 标签id
     * @param name 标签名
     * @return
     */
    @PostMapping("/update")
    public String update(Integer id, String name, RedirectAttributes redirectAttributes){
        Tag tag = tagService.getByName(name);
        if(tag != null){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "标签名称重复");
            redirectAttributes.addFlashAttribute(Constants.TAG_KEY, tag);
            return "redirect:/admin/tags/toEdit.do/" + id;
        }
        tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        tagService.updateById(tag);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "修改成功");
        return "redirect:/admin/tags";
    }

    /**
     * 删除标签
     * @param id 标签id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        boolean success = tagService.removeById(id);
        if(success){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "删除成功");
        }else{
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "删除失败");
        }
        return "redirect:/admin/tags";
    }
}
