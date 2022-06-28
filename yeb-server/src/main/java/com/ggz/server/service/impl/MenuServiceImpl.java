package com.ggz.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggz.server.mapper.MenuMapper;
import com.ggz.server.pojo.Menu;
import com.ggz.server.service.IMenuService;
import com.ggz.server.utils.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Menu> getMenuByAdminId() {
        Integer adminId = AdminUtils.getCurrentAdmin().getId();

//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);

        ListOperations<String, Menu> listOperations = redisTemplate.opsForList();
        List<Menu> menus = listOperations.range("menu_" + adminId, 0, -1L);

//        if (CollectionUtils.isEmpty(menus)) {
//            menus = menuMapper.getMenusByAdminId(adminId);
//            valueOperations.set("menu_" + adminId, menus,1,TimeUnit.MINUTES);
//            hashOperations.putAll("menu_" + adminId,);
//        }
//        return menus;

        if (CollectionUtils.isEmpty(menus)) {
            menus = menuMapper.getMenusByAdminId(adminId);
            listOperations.leftPushAll("menu_" + adminId, menus);
        }
        return menus;
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
