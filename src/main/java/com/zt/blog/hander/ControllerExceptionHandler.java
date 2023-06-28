package com.zt.blog.hander;

import com.zt.blog.exception.BusinessException;
import com.zt.blog.exception.EnumBusinessError;
import com.zt.blog.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec com.zt.blog.hander
 * @since 2023 - 04 - 20 - 14:58
 */
@ControllerAdvice   //给Controller控制器添加统一的操作或处理
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    //全局异常捕获
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL:{}, Exception:{}", request.getRequestURL(), e);
//        //当标识了状态码的时候就不拦截 交给SpringBoot处理
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
//            System.out.println(e.getMessage());
//            throw e;
//        }
        if (e instanceof BusinessException) {
            Map<String, Object> responseData = new HashMap<>();
            BusinessException businessException = (BusinessException) e;
            responseData.put("errorCode", businessException.getErrorCode());
            responseData.put("errorMessage", businessException.getErrorMessage());
            return CommonReturnType.create(responseData, "fail");
        } else {
            ModelAndView mv = new ModelAndView();
            mv.addObject("url",request.getRequestURL());
            mv.addObject("exception", e);
            mv.setViewName("error/error");
            return mv;
        }
    }
}
