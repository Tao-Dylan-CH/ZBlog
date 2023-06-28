package com.zt.blog.controller.vo;

import com.zt.blog.bean.Tag;
import lombok.Data;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller.vo
 * @since 2023-06-24 9:48
 */
@Data
public class TagVO extends Tag {
    private Integer blogCnt;
}
