package com.zt.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.blog.bean.Type;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_type】的数据库操作Service
* @createDate 2023-04-20 20:08:54
*/
public interface TypeService extends IService<Type> {
    /**
     * 根据分类名称模糊查询
     * @param name 分类名称
     * @param pageNo 页码
     * @param pageSize 每页最大行数
     */
    Page<Type> pageFuzzyByName(String name, Integer pageNo, Integer pageSize);

    //根据分类名称返回分类
    Type getByName(String name);
    //根据分类对应博客数目取前size个
    List<Type> listTop(Integer size);
}
