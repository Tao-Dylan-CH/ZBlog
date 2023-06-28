package com.zt.blog.controller;

import com.zt.blog.bean.Comment;
import com.zt.blog.bean.User;
import com.zt.blog.constant.Constants;
import com.zt.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller
 * @since 2023-06-25 7:36
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    //显示评论列表
    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Integer blogId, Model model){
        model.addAttribute(Constants.COMMENTS_KEY, commentService.listTopCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    //发布评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        User user = (User)session.getAttribute(Constants.SESSION_USER_KEY);
        if(user != null){//管理员发布
            //是否是管理员发布
            comment.setAdminComment(true);
            //头像
            comment.setAvatar(user.getAvatar());
        }else{
            comment.setAdminComment(false);
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlogId();
    }
}
