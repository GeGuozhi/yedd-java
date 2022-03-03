package com.ggz.server.controller;

import com.ggz.server.pojo.RespBean;
import com.ggz.server.service.SystemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置控制器
 *
 * @author ggz on 2022/2/28
 */
@RestController
@RequestMapping(value = "/system/init")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 初始化数据库
     * 执行配置文件中指定位置的SQL脚本(多行)
     */
    @ApiOperation(value = "初始化数据库")
    @GetMapping("/initDatabase")
    public RespBean initDatabase(){
        return systemService.initDatabase();
    }
}