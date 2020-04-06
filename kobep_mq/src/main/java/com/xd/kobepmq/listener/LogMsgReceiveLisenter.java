package com.xd.kobepmq.listener;

import cn.hutool.core.bean.BeanUtil;
import com.rabbitmq.client.Channel;
import com.xd.kobepcommon.constants.QueueConstants;
import com.xd.kobepcommon.dto.SysLogDTO;
import com.xd.kobepmq.entity.SysLog;
import com.xd.kobepmq.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 监听日志存储请求
 */
@Slf4j
@Component
public class LogMsgReceiveLisenter {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 日志队列消费端，存数据库
     * @param sysLogDTO
     * @param channel
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = QueueConstants.LOG_MSG_QUEUE)
    public void handler(SysLogDTO sysLogDTO, Channel channel, Message message) throws IOException {
        log.info("系统日志消费端成功消费信息：sysLog={}", sysLogDTO);
        // 确认消息接受
        String msgId = message.getMessageProperties().getHeader("spring_returned_message_correlation");
        log.info("日志id，msgId={}", msgId);
        SysLog sysLog = new SysLog();
        BeanUtil.copyProperties(sysLogDTO,sysLog);
        boolean flag = sysLogService.saveOrUpdate(sysLog);
        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();
        if (flag){
            //消息确认
            channel.basicAck(tag,false);
        }else {
            channel.basicNack(tag, false, true);
        }

    }
}
