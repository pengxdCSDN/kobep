package com.xd.kobepcommon.exception;


import com.xd.kobepcommon.core.Result;
import com.xd.kobepcommon.core.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理的全局配置
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handleServiceException(HttpServletRequest request, ServiceException ex) {
        log.error("exception error:{}",ex);
        return new Result(ex.getResultCode().getCode(),ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(HttpServletRequest request, Exception ex) {
        log.error("exception error:{}",ex);
        return ResultGenerator.genFailResult(ex.getMessage());
    }



}
