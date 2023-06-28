package com.zt.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.blog.bean.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_blog】的数据库操作Mapper
* @createDate 2023-06-21 22:26:56
* @Entity com.zt.blog.bean.Blog
*/
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    List<Integer> getTypeIdByTopN(Integer size);

    //查博客修改年份列表
    List<String> getAllYear();
    //根据年份获取博客列表
    List<Blog> listByYear(String year);
}




