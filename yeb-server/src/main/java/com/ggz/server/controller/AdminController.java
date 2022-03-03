package com.ggz.server.controller;


import com.ggz.server.pojo.Admin;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.pojo.Role;
import com.ggz.server.service.IAdminService;
import com.ggz.server.service.IRoleService;
import com.ggz.server.utils.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private IRoleService iRoleService;

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
    @DeleteMapping("/deleteAdmin/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {
        if (iAdminService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("updateAdminRole")
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        return iAdminService.updateAdminRole(adminId, rids);
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("roles")
    public List<Role> roles() {
        return iRoleService.list();
    }

    @ApiOperation(value = "用户中心更新密码")
    @PutMapping("/info/updatePassword")
    public RespBean updatePassword(@RequestBody Map<String, Object> passwordMap, Authentication authentication) {
        String oldPassword = String.valueOf(passwordMap.get("oldPassword"));
        String password = String.valueOf(passwordMap.get("pass"));
        Integer adminId = (Integer) passwordMap.get("adminId");
        return iAdminService.updatePasswordForUserCenter(adminId, oldPassword, password);
    }

    @ApiOperation(value = "用户中心更新操作员")
    @PutMapping("/info/updateAdminForUserCenter")
    public RespBean updateAdminForUserCenter(@RequestBody Admin admin, Authentication authentication) {
        if (iAdminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "用户中心更新头像")
    @PostMapping("/info/updatePictureForUserCenter")
    public RespBean updatePictureForUserCenter(MultipartFile file,Integer id, Authentication authentication) {
        String[] filePath = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl()+filePath[0]+"/"+filePath[1];
        return iAdminService.updatePictureForUserCenter(url,id,authentication);
    }

}
