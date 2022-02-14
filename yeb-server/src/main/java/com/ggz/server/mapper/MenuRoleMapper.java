package com.ggz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggz.server.pojo.MenuRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer insertRecord(Integer rid, Integer[] mids);
}
