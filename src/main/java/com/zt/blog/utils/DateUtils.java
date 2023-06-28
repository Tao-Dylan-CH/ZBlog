package com.zt.blog.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.utils
 * @since 2023 - 04 - 22 - 18:12
 */
public class DateUtils {
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date){
       return format.format(date);
    }
}
