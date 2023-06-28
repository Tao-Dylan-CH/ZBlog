package com.zt.blog.mapper;

import com.zt.blog.bean.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lenovo
* @description 针对表【t_comment】的数据库操作Mapper
* @createDate 2023-04-20 20:09:15
* @Entity com.zt.blog.bean.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




