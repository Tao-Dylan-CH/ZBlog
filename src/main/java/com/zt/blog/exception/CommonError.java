package com.zt.blog.exception;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec 通用错误类型
 * @since 2023 - 01 - 10 - 12:21
 */
public interface CommonError {
    Integer getErrorCode();
    String getErrorMessage();
    CommonError setErrorMessage(String errorMessage);
}
