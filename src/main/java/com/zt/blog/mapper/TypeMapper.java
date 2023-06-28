package com.zt.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.blog.bean.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author lenovo
* @description 针对表【t_type】的数据库操作Mapper
* @createDate 2023-04-20 20:08:54
* @Entity com.zt.blog.bean.Type
*/
@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    Type getByName(@Param("name") String name);
}




