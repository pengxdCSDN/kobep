package com.xd.kobepauth.handle;


import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 生成验证码
 * @author: pxd
 * @create: 2019-03-22 14:23
 **/
public interface ValidateCodeGenerateStrategy {

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    ValidateCodeDTO generate(ServletWebRequest request);


}
