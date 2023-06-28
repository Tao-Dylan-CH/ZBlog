package com.zt.blog.controller.vo;

import com.zt.blog.bean.Type;
import lombok.Data;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.controller.vo
 * @since 2023-06-24 9:46
 */
@Data
public class TypeVO extends Type {
    private Integer blogCnt;
}
