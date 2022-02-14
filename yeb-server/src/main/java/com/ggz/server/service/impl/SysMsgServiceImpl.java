package com.ggz.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggz.server.mapper.SysMsgMapper;
import com.ggz.server.pojo.SysMsg;
import com.ggz.server.service.ISysMsgService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@Service
public class SysMsgServiceImpl extends ServiceImpl<SysMsgMapper, SysMsg> implements ISysMsgService {

    @Override
    public boolean save(SysMsg entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<SysMsg> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<SysMsg> entityList) {
        return super.saveOrUpdateBatch(entityList);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return super.removeByMap(columnMap);
    }

    @Override
    public boolean remove(Wrapper<SysMsg> queryWrapper) {
        return super.remove(queryWrapper);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    public boolean updateById(SysMsg entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean update(Wrapper<SysMsg> updateWrapper) {
        return super.update(updateWrapper);
    }

    @Override
    public boolean update(SysMsg entity, Wrapper<SysMsg> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<SysMsg> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    public SysMsg getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<SysMsg> listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    @Override
    public List<SysMsg> listByMap(Map<String, Object> columnMap) {
        return super.listByMap(columnMap);
    }

    @Override
    public SysMsg getOne(Wrapper<SysMsg> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public int count() {
        return super.count();
    }

    @Override
    public int count(Wrapper<SysMsg> queryWrapper) {
        return super.count(queryWrapper);
    }

    @Override
    public List<SysMsg> list(Wrapper<SysMsg> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public List<SysMsg> list() {
        return super.list();
    }

    @Override
    public <E extends IPage<SysMsg>> E page(E page, Wrapper<SysMsg> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    @Override
    public <E extends IPage<SysMsg>> E page(E page) {
        return super.page(page);
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<SysMsg> queryWrapper) {
        return super.listMaps(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listMaps() {
        return super.listMaps();
    }

    @Override
    public List<Object> listObjs() {
        return super.listObjs();
    }

    @Override
    public <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return super.listObjs(mapper);
    }

    @Override
    public List<Object> listObjs(Wrapper<SysMsg> queryWrapper) {
        return super.listObjs(queryWrapper);
    }

    @Override
    public <V> List<V> listObjs(Wrapper<SysMsg> queryWrapper, Function<? super Object, V> mapper) {
        return super.listObjs(queryWrapper, mapper);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<SysMsg> queryWrapper) {
        return super.pageMaps(page, queryWrapper);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page) {
        return super.pageMaps(page);
    }

    @Override
    public QueryChainWrapper<SysMsg> query() {
        return super.query();
    }

    @Override
    public LambdaQueryChainWrapper<SysMsg> lambdaQuery() {
        return super.lambdaQuery();
    }

    @Override
    public UpdateChainWrapper<SysMsg> update() {
        return super.update();
    }

    @Override
    public LambdaUpdateChainWrapper<SysMsg> lambdaUpdate() {
        return super.lambdaUpdate();
    }

    @Override
    public boolean saveOrUpdate(SysMsg entity, Wrapper<SysMsg> updateWrapper) {
        return super.saveOrUpdate(entity, updateWrapper);
    }
}
