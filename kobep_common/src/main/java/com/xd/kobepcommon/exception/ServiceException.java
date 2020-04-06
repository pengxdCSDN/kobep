package com.xd.kobepcommon.exception;

import com.xd.kobepcommon.core.ResultCode;
import lombok.Data;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
@Data
public class ServiceException extends RuntimeException {

    private ResultCode resultCode;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }


    public ServiceException(ResultCode resultCode,String message) {
        super(message);
        this.resultCode = resultCode;
    }
}