package com.zt.blog.service;

import com.zt.blog.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lenovo
* @description 针对表【t_user】的数据库操作Service
* @createDate 2023-04-20 20:08:17
*/
public interface UserService extends IService<User> {
    User checkUser(String username, String password);

}
