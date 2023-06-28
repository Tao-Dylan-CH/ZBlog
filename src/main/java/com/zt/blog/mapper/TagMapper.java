package com.zt.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.blog.bean.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author lenovo
* @description 针对表【t_tag】的数据库操作Mapper
* @createDate 2023-04-20 20:09:03
* @Entity com.zt.blog.bean.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    Tag getByName(@Param("name") String name);

}




