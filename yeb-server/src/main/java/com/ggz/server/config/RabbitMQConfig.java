package com.ggz.server.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ggz.mail.MailConstants;
import com.ggz.server.pojo.MailLog;
import com.ggz.server.service.IMailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置
 *
 * @author ggz on 2022/2/15
 */
@Configuration
public class RabbitMQConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    private final CachingConnectionFactory cachingConnectionFactory;

    private final IMailLogService iMailLogService;

    public RabbitMQConfig(CachingConnectionFactory cachingConnectionFactory, IMailLogService iMailLogService) {
        this.cachingConnectionFactory = cachingConnectionFactory;
        this.iMailLogService = iMailLogService;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         *  消息确认回调
         *  data:消息唯一标识
         *  ack:确认结果
         *  cause:失败原因
         */
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            String msgId = data.getId();
            if (ack) {
                logger.info("{}=====>消息发送成功", msgId);
                iMailLogService.update(new UpdateWrapper<MailLog>().set("status", 1).eq("msgId", msgId));
            } else {
                logger.error("{}=====>消息发送失败", msgId);
            }
        });

        /**
         * 消息失败回调，router不到queue回调
         * msg:消息主题
         * repCode:响应码
         * repText:相应描述
         * exchange:交换机
         * routingKey:路由键
         */
        rabbitTemplate.setReturnCallback((msg, repCode, repText, exchange, routingKey) -> {
            logger.error("{}=====>消息发送Queue失败", msg.getBody());
        });

        return rabbitTemplate;
    }

    @Bean
    public Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}