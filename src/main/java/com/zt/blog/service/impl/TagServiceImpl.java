package com.zt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.blog.bean.Tag;
import com.zt.blog.mapper.BlogTagMapper;
import com.zt.blog.mapper.TagMapper;
import com.zt.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_tag】的数据库操作Service实现
* @createDate 2023-04-20 20:09:03
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public Tag getByName(String name) {
        return tagMapper.getByName(name);
    }

    @Override
    public Page<Tag> pageFuzzyByName(Integer pageNo, Integer pageSize, String name) {
        Page<Tag> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Tag::getName, name);
        wrapper.orderByAsc(Tag::getName);
        return tagMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Tag> listTop(Integer size) {
        List<Integer> tagIdList = blogTagMapper.getTagIdByTopN(size);
        return listByIds(tagIdList);
    }

    @Override
    public List<Tag> listTags(List<Integer> tagIds) {
        return null;
    }
}




