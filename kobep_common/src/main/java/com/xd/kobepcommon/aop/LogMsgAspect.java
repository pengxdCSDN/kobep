package com.xd.kobepcommon.aop;

import cn.hutool.core.lang.ObjectId;
import com.alibaba.fastjson.JSON;
import com.xd.kobepcommon.annotation.LogMsg;
import com.xd.kobepcommon.constants.ExchangeConstants;
import com.xd.kobepcommon.constants.RoutingConstants;
import com.xd.kobepcommon.dto.SysLogDTO;
import com.xd.kobepcommon.enums.OperationStatusEnum;
import com.xd.kobepcommon.enums.SysLogTypeEnum;
import com.xd.kobepcommon.util.IpUtil;
import com.xd.kobepcommon.util.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class LogMsgAspect {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Around(value = "@annotation(com.xd.kobepcommon.annotation.LogMsg)")
    public Object handlerMethod(ProceedingJoinPoint pjp) {
        Object result = null;
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        long startTime = System.currentTimeMillis();
        SysLogDTO sysLogDTO = null;
        HttpServletRequest httpServletRequest = null;
        if (targetMethod.isAnnotationPresent(LogMsg.class)) {
            LogMsg logMsg = targetMethod.getAnnotation(LogMsg.class);
            sysLogDTO = new SysLogDTO();
            httpServletRequest = IpUtil.getHttpServletRequest();
            if ("" != logMsg.actionName() && SysLogTypeEnum.LOGIN.getMessage().equals(logMsg.actionName())) {
                sysLogDTO.setType(SysLogTypeEnum.LOGIN.getCode());
            } else {
                sysLogDTO.setType(SysLogTypeEnum.OPERATOR.getCode());
            }
            sysLogDTO.setActionName(logMsg.actionName())
                    .setId(SnowflakeUtil.nextId(2, 2))
                    //controller执行方法的controller类
                    .setClassName(signature.getDeclaringTypeName())
                    //controller请求的方法名
                    .setMethod(httpServletRequest.getMethod())
                    .setMethod(httpServletRequest.getMethod())
                    .setRequestUri(httpServletRequest.getRequestURI())
                    .setUserAgent(httpServletRequest.getHeader("user-agent"))
                    .setParams(JSON.toJSONString(httpServletRequest.getParameterMap()))
                    .setCreateBy(httpServletRequest.getHeader("username"))
                    .setRemoteAddr(IpUtil.getRemoteHost(httpServletRequest));
            try {
                result = pjp.proceed();
                sysLogDTO.setStatus(OperationStatusEnum.SUCCESS.getCode());
            } catch (Throwable e) {
                sysLogDTO.setException(IpUtil.getTrace(e));
                sysLogDTO.setStatus(OperationStatusEnum.FAIL.getCode());
            }
            // 本次操作用时（毫秒）
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("[{}]use time: {}ms", pjp.getSignature(), elapsedTime);
            sysLogDTO.setTime(elapsedTime);
            // 发送消息到 系统日志队列
            String msgId = ObjectId.next();
            CorrelationData correlationData = new CorrelationData(msgId);
            if (targetMethod.isAnnotationPresent(LogMsg.class)) {
                rabbitTemplate.convertAndSend(ExchangeConstants.LOG_MSG_EXCHANGE, RoutingConstants.LOG_MSG_ROUTING, sysLogDTO, correlationData);
            }

        }

        return result;
    }


}
