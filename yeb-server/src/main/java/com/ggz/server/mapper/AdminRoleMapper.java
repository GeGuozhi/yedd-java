package com.ggz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggz.server.pojo.AdminRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer addAdminRole(Integer adminId, Integer[] rids);
}
