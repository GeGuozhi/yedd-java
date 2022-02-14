package com.ggz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggz.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenusByAdminId(Integer id);

    List<Menu> getAllMenus();
}
