package com.zt.blog;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.blog.bean.Type;
import com.zt.blog.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.PSource;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog
 * @since 2023 - 04 - 21 - 13:54
 */
@SpringBootTest
public class TypeServiceTest {
    @Autowired
    private TypeService typeService;
    @Test
    void testSave() {
        Type type = new Type();
        type.setName("清单");
        typeService.save(type);
    }

    @Test
    void testSelect(){
        Type type = typeService.getById(1);
        System.out.println(type);
    }

    @Test
    void testUpdate(){
        Type type = new Type();
        type.setId(1);
        type.setName("test");
        typeService.updateById(type);

    }

    @Test
    void testPage(){
        Page<Type> page = typeService.pageFuzzyByName("", 1, 2);
        //是否有下一页
        System.out.println(page.hasNext());
        //首页数
        System.out.println(page.getPages());
        System.out.println(page);
        System.out.println(page.getRecords());
    }
}
