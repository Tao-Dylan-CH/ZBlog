package com.zt.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.blog.bean.Type;
import com.zt.blog.constant.Constants;
import com.zt.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec
 * @since 2023 - 04 - 21 - 12:18
 */
@Controller
@RequestMapping("/admin/types")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 每页显示条数
     * @param name 标签名
     */
    @GetMapping
    public String list(@RequestParam(defaultValue = "1") Integer pageNo,
                       @RequestParam(defaultValue = Constants.DISPLAY_PAGE_SIZE) Integer pageSize,
                       @RequestParam(defaultValue = "") String name, Model model){
        Page<Type> page = typeService.pageFuzzyByName(name, pageNo, pageSize);
        model.addAttribute(Constants.PAGE_KEY, page);
        return "/admin/types";
    }

    /**
     * 跳转到添加分类页面
     */
    @GetMapping("/add")
    public String add(){
        return "/admin/types-input";
    }

    /**
     * 保存分类
     * @param name
     */
    @PostMapping("/save")
    public String save(String name, RedirectAttributes redirectAttributes){
        Type type = new Type();
        type.setName(name);
        //判断添加名称是否重复
        if(typeService.getByName(name) != null){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "类型名称已存在");
            //用于回显
            redirectAttributes.addFlashAttribute(Constants.TYPE_KEY, type);
            return "redirect:/admin/types/add";
        }

        boolean success = typeService.save(type);
        if(success){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "添加成功");
        }else{
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "添加失败");
        }
        return "redirect:/admin/types";
    }

    /**
     * 跳转到编辑页面
     * @param id
     */
    @GetMapping("/update/{id}")
    public String edit(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Type type = typeService.getById(id);
        if(type == null){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "类型名称不存在");
            return "redirect:/admin/types";
        }
        request.setAttribute(Constants.TYPE_KEY, type);
        return "/admin/types-input";
    }

    /**
     * 保存修改
     * @param id
     * @param name
     */
    @PostMapping("/update")
    public String update(Integer id, String name, RedirectAttributes redirectAttributes){
        //检测类型名是否重复
        Type type = typeService.getByName(name);
        if(type != null){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "不能添加重复的分类");
            return "redirect:/admin/types/update/" + id;
        }
        type = new Type();
        //修改
        type.setId(id);
        type.setName(name);
        boolean success = typeService.updateById(type);
        if(success){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "修改成功");
        }else{
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "修改失败");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        boolean success = typeService.removeById(id);
        if(success){
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "删除成功");
        }else{
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_KEY, "删除失败");
        }
        return "redirect:/admin/types";
    }
}
