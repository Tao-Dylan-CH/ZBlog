package com.zt.blog.constant;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec 公共字段命名
 * @since 2023 - 04 - 20 - 21:11
 */
public class Constants {
    //session中用户键
    public static final String SESSION_USER_KEY = "user";
    //分页显示每页的最大条数
    public static final String DISPLAY_PAGE_SIZE = "3";
    //页面对象在域中的key
    public static final String PAGE_KEY = "page";

    //返回给前端的消息在域中的key
    public static final String MESSAGE_KEY = "message";

    //保存在域中的key
    public static final String TYPE_KEY = "type";
    public static final String TYPES_KEY = "types";
    public static final String TAG_KEY = "tag";
    public static final String TAGS_KEY = "tags";
    public static final String BLOG_KEY = "blog";
    public static final String COMMENTS_KEY = "comments";


    public static final Integer SHOW_TYPE_SIZE = 6;//前端展示类型标签数
    public static final Integer SHOW_TAG_SIZE = 10;//前端展示标签数
    public static final Integer RECOMMEND_BLOG_SIZE = 10;//前端展示推荐博客数量
    public static final String  RECOMMEND_BLOG = "recommendBlogList";//前端展示推荐博客列表key
}
