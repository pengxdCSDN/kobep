package com.xd.kobepcommon.core;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.HTTP_REQUEST_OK.getCode())
                .setMsg(ResultCode.HTTP_REQUEST_OK.getMsg());

    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result(ResultCode.HTTP_REQUEST_OK)
                .setData(data);

    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.SYSTEM_ERR.getCode())
                .setMsg(message);
    }
}

