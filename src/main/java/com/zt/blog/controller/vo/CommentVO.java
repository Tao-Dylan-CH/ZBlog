package com.zt.blog.controller.vo;

import com.zt.blog.bean.Comment;

import java.util.List;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller.vo
 * @since 2023-06-24 19:07
 */
public class CommentVO extends Comment {
    //子级评论
    private List<Comment> replyComments;
    //父级评论
    private Comment parentComment;
}
