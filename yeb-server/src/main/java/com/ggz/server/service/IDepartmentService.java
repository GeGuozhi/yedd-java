package com.ggz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggz.server.pojo.Department;
import com.ggz.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> getAllDepartments(Integer parentId);

    RespBean addDep(Department department);

    RespBean deleteDep(Integer id);
}
