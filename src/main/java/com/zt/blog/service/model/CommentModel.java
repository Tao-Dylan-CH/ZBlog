package com.zt.blog.service.model;

import com.zt.blog.bean.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.service.model
 * @since 2023-06-25 10:47
 */
@Data
public class CommentModel extends Comment {
    private List<Comment> replyComments;
}
