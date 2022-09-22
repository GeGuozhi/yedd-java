package com.ggz.mail;

public class MailConstants {

    /**
     * 消息投递中
     */
    public static final Integer DELIVERING = 0;

    /**
     * 消息投递成功
     */
    public static final Integer DELIVER_SUCCESS = 1;

    /**
     * 投递失败
     */
    public static final Integer DELIVER_FAILURE = 2;

    /**
     * 最大尝试数
     */
    public static final Integer MAX_TRY_COUNT = 3;

    /**
     * 消息超过时间
     */
    public static final Integer MSG_TIMEOUT = 1;

    /**
     * 队列
     */
    public static final String MAIL_QUEUE_NAME = "mail.queue";

    /**
     *
     */
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";

    /**
     *
     */
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";
}
