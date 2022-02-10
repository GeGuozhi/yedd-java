package com.xxxx.server.controller;


import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/getAllAdmins")
    public List<Admin> getAllAdmins(String keywords) {
        return iAdminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping("updateAdmin")
    public RespBean updateAdmin(@RequestBody Admin admin) {
        if (iAdminService.updateById(admin)) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("deleteAdmin")
    public RespBean deleteAdmin(@RequestBody Admin admin) {
        if (iAdminService.removeById(admin)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("updateAdminRole")
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        return iAdminService.updateAdminRole(adminId, rids);
    }


}
