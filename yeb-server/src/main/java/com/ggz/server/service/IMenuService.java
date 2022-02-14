package com.ggz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggz.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface IMenuService extends IService<Menu> {


    List<Menu> getMenuByAdminId();

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
