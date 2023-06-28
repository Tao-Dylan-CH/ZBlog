package com.zt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.blog.bean.BlogTag;
import com.zt.blog.mapper.BlogTagMapper;
import com.zt.blog.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lenovo
 * @description 针对表【t_blog_tag】的数据库操作Service实现
 * @createDate 2023-04-20 20:09:25
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag>
        implements BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public void deleteByBlogId(Integer blogId) {
        blogTagMapper.deleteByBlogId(blogId);
    }

    @Override
    public String getTagIdsByBlogId(Integer blogId) {
        List<BlogTag> list = blogTagMapper.getBlogTagByBlogId(blogId);
        StringBuilder builder = new StringBuilder();
        for (BlogTag blogTag : list) {
            builder.append(blogTag.getTagId()).append(",");
        }
        if (builder.length() > 0)
            return builder.substring(0, builder.length() - 1);
        return "";
    }

    @Override
    public List<Integer> listTagIdsByBlogId(Integer blogId) {
        List<BlogTag> list = blogTagMapper.getBlogTagByBlogId(blogId);
        return list.stream().map(BlogTag::getTagId).collect(Collectors.toList());
    }

    @Override
    public List<Integer> listBlogIdsByTagId(Integer tagId) {
        LambdaQueryWrapper<BlogTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlogTag::getTagId, tagId);
        return list(wrapper).stream().map(BlogTag::getBlogId).collect(Collectors.toList());
    }
}




