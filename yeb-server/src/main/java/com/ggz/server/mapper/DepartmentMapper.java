package com.ggz.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggz.server.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartments(Integer parendId);

    void addDep(Department department);

    void deleteDep(Department department);
}
