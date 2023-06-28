package com.zt.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.blog.bean.Comment;
import com.zt.blog.service.model.CommentModel;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_comment】的数据库操作Service
* @createDate 2023-04-20 20:09:15
*/
public interface CommentService extends IService<Comment> {
    //根据博客id获取顶级评论列表
    List<CommentModel> listTopCommentByBlogId(Integer blogId);

    //添加评论
    Comment saveComment(Comment comment);
}
