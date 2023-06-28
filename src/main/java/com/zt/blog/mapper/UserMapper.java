package com.zt.blog.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zt.blog.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lenovo
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2023-04-20 20:08:17
* @Entity com.zt.blog.bean.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}




