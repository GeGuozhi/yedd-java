package com.ggz.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ggz.server.pojo.Menu;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.pojo.Role;
import com.ggz.server.service.IMenuRoleService;
import com.ggz.server.service.IMenuService;
import com.ggz.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ggz.server.pojo.MenuRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组
 *
 * @author ggz on 2022/2/9
 */
@RestController
@RequestMapping(value = "/system/basic/permiss")
public class PermissionController {
    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IMenuService iMenuService;

    @Autowired
    private IMenuRoleService iMenuRoleService;

    @ApiOperation(value = "获取所有角色信息")
    @GetMapping(value = "/getAllRoles")
    public List<Role> getAllRoles() {
        return iRoleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping(value = "/addRole")
    public RespBean addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (iRoleService.save(role)) {
            return RespBean.success("添加成功！");
        } else {
            return RespBean.error("添加失败!");
        }
    }

    @ApiOperation(value = "更新职称信息")
    @PutMapping(value = "/updateRole")
    public RespBean updateRole(@RequestBody Role role) {
        if (iRoleService.updateById(role)) {
            return RespBean.success("更新成功！");
        } else {
            return RespBean.error("更新失败!");
        }
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping(value = "/delRole/{rid}")
    public RespBean delRole(@PathVariable Integer rid) {
        if (iRoleService.removeById(rid)) {
            return RespBean.success("删除成功！");
        } else {
            return RespBean.error("删除失败!");
        }
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping(value = "/menu")
    public List<Menu> getAllMenus() {
        return iMenuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色ID查询所有菜单")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMenuIdByRoleId(@PathVariable Integer rid) {
        return iMenuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid)).stream()
                .map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/updateMenuRole")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        return iMenuRoleService.updateMenuRole(rid,mids);
    }

}