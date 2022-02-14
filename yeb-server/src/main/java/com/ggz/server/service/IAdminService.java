package com.ggz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggz.server.pojo.Admin;
import com.ggz.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登录返回token
     * @param username
     * @param password
     * @param code
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);
    /**
     * 根据用户名获取用户
     * @param username
     */
    Admin getAdminByUserName(String username);

    /**
     * 获取所有操作员
     * @param keywords
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);
}
