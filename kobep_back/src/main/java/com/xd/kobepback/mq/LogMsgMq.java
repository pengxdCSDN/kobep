package com.xd.kobepback.mq;

import com.xd.kobepcommon.constants.ExchangeConstants;
import com.xd.kobepcommon.constants.QueueConstants;
import com.xd.kobepcommon.constants.RoutingConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志mq
 */
@Configuration
public class LogMsgMq {

    @Bean
    public Queue logMsgQueue(){
        return new Queue(QueueConstants.LOG_MSG_QUEUE,true);
    }

    @Bean
    public DirectExchange logMsgExchange(){
        return new DirectExchange(ExchangeConstants.LOG_MSG_EXCHANGE,true,false);
    }

    @Bean
    public Binding LogMsgBind(){
        return BindingBuilder.bind(logMsgQueue()).to(logMsgExchange()).with(RoutingConstants.LOG_MSG_ROUTING);
    }

}
