package com.ggz.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ggz.mail.MailConstants;
import com.ggz.server.pojo.Employee;
import com.ggz.server.pojo.MailLog;
import com.ggz.server.service.IEmployeeService;
import com.ggz.server.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务
 *
 * @author ggz on 2022/2/15
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService iMailLogService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "* * 22-23 * * ?")
    public void mailTask() {
        //当前时间小于 tryTime 则进行查询，tryTime为上一次发送的一分钟后。
        List<MailLog> mailLogList = iMailLogService.list(
                new QueryWrapper<MailLog>().
                        eq("status", MailConstants.DELIVERING).
                        lt("tryTime", LocalDateTime.now()));
        mailLogList.forEach(mailLog -> {
            if (mailLog.getCount() >= 3) {
                iMailLogService.update(
                        new UpdateWrapper<MailLog>().
                                set("status", MailConstants.DELIVER_FAILURE).
                                eq("msgId", mailLog.getMsgId()));
            }
            iMailLogService.update(
                    new UpdateWrapper<MailLog>().
                            set("count", mailLog.getCount() + 1).
                            set("updateTime",LocalDateTime.now()).
                            set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)).
                            eq("msgId", mailLog.getMsgId()));
            Employee emp = iEmployeeService.getEmployee(mailLog.getEid()).get(0);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp,
                    new CorrelationData(mailLog.getMsgId()));
        });
    }
}