package com.zt.blog.exception;

/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec 业务错误枚举
 * @since 2023 - 01 - 10 - 12:23
 */
public enum EnumBusinessError implements CommonError{
    //通用错误类型 10001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    //未知错误
    UNKNOWN_ERROR(10002, "未知错误"),
    //20000 开头为用户信息相关错误码
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户名或密码不正确"),
    USER_ONT_LOGIN(20003, "用户还未登录"),

    //30000 开头为交易信息相关错误码
    STACK_NOT_ENOUGH(30001, "库存不足")
    ;
    //自定义错误码
    private int errorCode;
    //自定义错误信息
    private String errorMessage;

    EnumBusinessError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public CommonError setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
