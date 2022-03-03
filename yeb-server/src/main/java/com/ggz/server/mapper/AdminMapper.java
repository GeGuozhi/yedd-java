package com.ggz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggz.server.pojo.Admin;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> getAllAdmins(Integer id, String keywords);

    Admin getAdminByUserName(String username);
}
