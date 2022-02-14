package com.ggz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggz.server.pojo.MenuRole;
import com.ggz.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色id
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
