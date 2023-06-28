package com.zt.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.blog.bean.Tag;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_tag】的数据库操作Service
* @createDate 2023-04-20 20:09:03
*/
public interface TagService extends IService<Tag> {
    Tag getByName(String name);

    Page<Tag> pageFuzzyByName(Integer pageNo, Integer pageSize, String name);
    //根据标签对应博客数目取前size个
    List<Tag> listTop(Integer size);
    //根据tagIds 返回tags
    List<Tag> listTags(List<Integer> tagIds);
}

