package com.ggz.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 聊天类
 *
 * @author ggz on 2022/2/16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ChatMsg {
    private String from;
    private String to;
    private String content;
    private LocalDateTime date;
    private String fromNickName;
}