package com.zt.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.blog.bean.Blog;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_blog】的数据库操作Service
* @createDate 2023-06-21 22:26:56
*/
public interface BlogService extends IService<Blog> {
    Page<Blog> pageList(Integer pageNo, Integer pageSize, String title, Integer typeId, Boolean recommend);
    Page<Blog> pageOrderByUpdateTime(Integer pageNo, Integer pageSize);
    //根据类型对应的博客数量取前N个类型Id
    List<Integer> getTypeIdByTopN(Integer size);
    //根据修改时间取前N个推荐博客
    List<Blog> listRecommendBlogTop(Integer size);
    //根据标题 或 描述 模糊查询
    Page<Blog> pageLikeByTitleOrDescription(Integer pageNo, Integer pageSize, String query);
    //根据id获取博客，将markdown内容转为html
    Blog getAndConvert(Integer id);
    //根据typeId 分页查询
    Page<Blog> pageListByTypeId(Integer typeId, Integer pageNo, Integer pageSize);
    //Increase page views
    void increasePageView(Blog blog);
    //根据tagId 分页查询
    Page<Blog> pageByBlogIds(List<Integer> blogIds, Integer pageNo, Integer pageSize);
    //查博客修改年份列表
    List<String> getAllYear();
    //根据年份获取博客列表
    List<Blog> listBlogByYear(String year);
}
