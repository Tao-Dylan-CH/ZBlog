package com.zt.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.blog.bean.BlogTag;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_blog_tag】的数据库操作Service
* @createDate 2023-04-20 20:09:25
*/
public interface BlogTagService extends IService<BlogTag> {
    //根据博客id删除博客和标签的关联关系
    void deleteByBlogId(Integer blogId);

    //根据博客id获取绑定的标签id    id1,id2
    String getTagIdsByBlogId(Integer blogId);
    //根据博客id获取绑定的标签id列表
    List<Integer> listTagIdsByBlogId(Integer blogId);
    //根据标签id获取绑定的博客id列表
    List<Integer> listBlogIdsByTagId(Integer tagId);
}
