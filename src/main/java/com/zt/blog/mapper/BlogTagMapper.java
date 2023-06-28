package com.zt.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.blog.bean.BlogTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_blog_tag】的数据库操作Mapper
* @createDate 2023-04-20 20:09:25
* @Entity com.zt.blog.bean.BlogTag
*/
@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTag> {
    int deleteByBlogId(@Param("blogId") Integer blogId);

    //查询blog绑定的标签id
    List<BlogTag> getBlogTagByBlogId(@Param("blogId") Integer blogId);

    //根据标签对应的博客数量取前N个标签id
    List<Integer> getTagIdByTopN(Integer size);
}




