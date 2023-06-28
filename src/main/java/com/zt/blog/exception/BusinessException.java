package com.zt.blog.exception;


/**
 * @author 挚爱之夕
 * @version 1.0
 * @implSpec 包装器模式 业务异常类实现
 * @since 2023 - 01 - 10 - 12:30
 */
public class BusinessException extends Exception implements CommonError{
    private CommonError commonError;

    //直接接收EmBusinessError的传参用于构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    //接收自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError, String errorMessage){
        this(commonError);
        this.commonError.setErrorMessage(errorMessage);
    }
    @Override
    public Integer getErrorCode() {
        return commonError.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return commonError.getErrorMessage();
    }

    @Override
    public CommonError setErrorMessage(String errorMessage) {
        commonError.setErrorMessage(errorMessage);
        return this;
    }
}
