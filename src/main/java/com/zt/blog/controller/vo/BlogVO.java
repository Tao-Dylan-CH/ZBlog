package com.zt.blog.controller.vo;

import com.zt.blog.bean.Blog;
import com.zt.blog.bean.Tag;
import com.zt.blog.bean.User;
import com.zt.blog.utils.DateUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller.vo
 * @since 2023 - 04 - 22 - 18:09
 */
@Data
public class BlogVO extends Blog {
    //对应分类名称
    private String type;
    private String updateTimeStr;
    private String createTimeStr;
    //标签id id1,id2
    private String tagIds;
    private List<Tag> tags;
    //作者
    private User user;
    public static BlogVO convert(Blog blog){
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(blog, blogVO);
        blogVO.setUpdateTimeStr(DateUtils.format(blog.getUpdateTime()));
        blogVO.setCreateTimeStr(DateUtils.format(blog.getCreateTime()));
        return blogVO;
    }
}
