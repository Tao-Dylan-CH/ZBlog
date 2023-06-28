package com.zt.blog.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_blog
 */
@TableName(value ="t_blog")
@Data
public class Blog implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客首图url
     */
    private String firstPicture;

    /**
     * 标记: 原创 转载 翻译
     */
    private String flag;

    /**
     * 浏览次数
     */
    private Integer viewCnt;

    /**
     * 是否开启赞赏
     */
    private Boolean appreciation;

    /**
     * 是否开启显示版权信息
     */
    private Boolean shareStatement;

    /**
     * 是否开启评论
     */
    private Boolean reviewable;

    /**
     * 是否推荐
     */
    private Boolean recommend;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 所属类型id
     */
    private Integer typeId;

    /**
     * 所属用户id
     */
    private Integer userId;

    /**
     * 博客描述
     */
    private String description;

    /**
     * true为已经发布 false 为保存
     */
    private Boolean published;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}