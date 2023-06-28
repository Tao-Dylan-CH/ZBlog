package com.zt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.blog.bean.Comment;
import com.zt.blog.mapper.CommentMapper;
import com.zt.blog.service.CommentService;
import com.zt.blog.service.model.CommentModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author lenovo
* @description 针对表【t_comment】的数据库操作Service实现
* @createDate 2023-04-20 20:09:15
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Autowired
    private CommentMapper commentMapper;

    //返回博客下的评论列表
    @Override
    @Transactional
    public List<CommentModel> listTopCommentByBlogId(Integer blogId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getBlogId, blogId);
        wrapper.eq(Comment::getParentCommentId, -1);
        wrapper.orderByAsc(Comment::getCreateTime);
        //顶级评论
        List<Comment> comments = this.list(wrapper);
        List<CommentModel> commentModelList = comments.stream().map(comment -> {
            CommentModel model = new CommentModel();
            BeanUtils.copyProperties(comment, model);
            return model;
        }).collect(Collectors.toList());
        //将其下的评论都作为二级评论(设置model.replyComments)
        for(CommentModel commentModel :  commentModelList){
            Integer id = commentModel.getId();
            //压缩，该评论下的所有回复都作为二级评论
            dfs(id);
            //回复按创建时间排序
            tempList.sort((e1, e2) -> {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStr1 = format.format(e1.getCreateTime());
                String timeStr2 = format.format(e2.getCreateTime());
                return timeStr1.compareTo(timeStr2);
            });
            commentModel.setReplyComments(tempList);
            tempList = new ArrayList<>();
        }
        return commentModelList;
    }
    //递归查询评论下的所有回复
    private List<Comment> tempList = new ArrayList<>();
    private void dfs(Integer parentCommentId){
        List<Comment> childComment = getChildComment(parentCommentId);
        if(childComment == null || childComment.size() == 0){
            return;
        }
        //设置被会回复人别名
        for(Comment c : childComment){
            c.setParentNickname(getById(parentCommentId).getNickname());
        }
        tempList.addAll(childComment);
        //子评论还有子评论，递归
        for(Comment c : childComment){
            dfs(c.getId());
        }
    }
    //保存评论
    @Override
    public Comment saveComment(Comment comment) {
        Integer parentCommentId = comment.getParentCommentId();
        comment.setCreateTime(new Date());
        save(comment);
        return comment;
    }
    //获取parentCommentId 对应的回复评论
    public List<Comment> getChildComment(Integer parentCommentId){
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentCommentId, parentCommentId);
        wrapper.orderByDesc(Comment::getCreateTime);
        return this.list(wrapper);
    }
}




