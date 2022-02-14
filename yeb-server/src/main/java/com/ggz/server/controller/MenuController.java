package com.ggz.server.controller;


import com.ggz.server.pojo.Menu;
import com.ggz.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    @ApiOperation(value = "查询菜单列表")
    @GetMapping("menu")
    public List<Menu> getMenuById(){
        return iMenuService.getMenuByAdminId();
    }
}
