package com.zt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.blog.bean.Type;
import com.zt.blog.mapper.BlogMapper;
import com.zt.blog.mapper.TypeMapper;
import com.zt.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_type】的数据库操作Service实现
* @createDate 2023-04-20 20:08:54
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Page<Type> pageFuzzyByName(String name, Integer pageNo, Integer pageSize) {
        Page<Type> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Type::getName, name);
        wrapper.orderByAsc(Type::getName);
        return typeMapper.selectPage(page, wrapper);
    }

    @Override
    public Type getByName(String name) {
        return typeMapper.getByName(name);
    }

    @Override
    public List<Type> listTop(Integer size) {
        List<Integer> typeIds = blogMapper.getTypeIdByTopN(size);
        return listByIds(typeIds);
    }
}




