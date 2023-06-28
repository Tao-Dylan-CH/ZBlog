package com.zt.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.blog.bean.User;
import com.zt.blog.service.UserService;
import com.zt.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-04-20 20:08:17
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public User checkUser(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }
}




