package com.xxxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页公共返回对象
 *
 * @author ggz on 2022/2/11
 */
@Data
//无参构造
@NoArgsConstructor
//全参数构造
@AllArgsConstructor
public class RespPageBean {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 数据List
     */
    private List<?> data;

}