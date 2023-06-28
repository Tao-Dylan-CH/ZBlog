package com.zt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.blog.bean.Blog;
import com.zt.blog.mapper.BlogMapper;
import com.zt.blog.service.BlogService;
import com.zt.blog.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lenovo
 * @description 针对表【t_blog】的数据库操作Service实现
 * @createDate 2023-06-21 22:26:56
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
        implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Page<Blog> pageList(Integer pageNo, Integer pageSize, String title, Integer typeId, Boolean recommend) {
        Page<Blog> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        //根据title模糊查询
        wrapper.like(title != null, Blog::getTitle, title);
        //根据分类id查询
        wrapper.eq(typeId != null, Blog::getTypeId, typeId);
        //是否推荐
        wrapper.eq(recommend != null, Blog::getRecommend, recommend);
        //排序
        wrapper.orderByDesc(Blog::getCreateTime).orderByDesc(Blog::getUpdateTime);
        return blogMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<Blog> pageOrderByUpdateTime(Integer pageNo, Integer pageSize) {
        Page<Blog> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Blog::getUpdateTime);
        return page(page, wrapper);
    }

    @Override
    public List<Integer> getTypeIdByTopN(Integer size) {
        return blogMapper.getTypeIdByTopN(size);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Blog::getRecommend, true);
        wrapper.orderByDesc(Blog::getUpdateTime);
        wrapper.last(" limit " + size);
        return blogMapper.selectList(wrapper);
    }

    @Override
    public Page<Blog> pageLikeByTitleOrDescription(Integer pageNo, Integer pageSize, String query) {
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Blog::getTitle, query).or().like(Blog::getContent, query);
        wrapper.orderByDesc(Blog::getUpdateTime);
        Page<Blog> page = new Page<>(pageNo, pageSize);
        return page(page, wrapper);
    }

    @Override
    public Blog getAndConvert(Integer id) {
        Blog blog = getById(id);
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        return blog;
    }

    @Override
    public Page<Blog> pageListByTypeId(Integer typeId, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Blog::getTypeId, typeId);
        wrapper.orderByDesc(Blog::getUpdateTime);
        Page<Blog> page = new Page<>(pageNo, pageSize);
        return page(page, wrapper);
    }

    @Override
    public void increasePageView(Blog blog) {
        LambdaUpdateWrapper<Blog> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Blog::getId, blog.getId());
        updateWrapper.set(Blog::getViewCnt, blog.getViewCnt() + 1);
        update(updateWrapper);
    }

    @Override
    public Page<Blog> pageByBlogIds(List<Integer> blogIds, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Blog::getId, blogIds);
        wrapper.orderByDesc(Blog::getUpdateTime);
        Page<Blog> page = new Page<>(pageNo, pageSize);
        return page(page, wrapper);
    }

    @Override
    public List<String> getAllYear() {
        return blogMapper.getAllYear();
    }

    @Override
    public List<Blog> listBlogByYear(String year) {
        return blogMapper.listByYear(year);
    }


}







