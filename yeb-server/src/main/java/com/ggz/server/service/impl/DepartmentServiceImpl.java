package com.ggz.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggz.server.mapper.DepartmentMapper;
import com.ggz.server.pojo.Department;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;


    /**
     * 获取所有部门
     *
     * @return
     */
    @Override
    public List<Department> getAllDepartments(Integer parentId) {
        return departmentMapper.getAllDepartments(parentId);
    }

    @Override
    public RespBean addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        department.setChildren(new ArrayList<>());
        if (1 == department.getResult()) {
            return RespBean.success("添加部门成功!", department);
        }
        return RespBean.error("添加部门失败!");
    }

    @Override
    public RespBean deleteDep(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if (-2 == department.getResult()) {
            return RespBean.error("该部门下还有子部门,删除失败！");
        } else if (-1 == department.getResult()) {
            return RespBean.error("该部门下还有员工,删除失败！");
        } else if (1 == department.getResult()) {
            return RespBean.success("删除成功！");
        } else {
            return RespBean.error("删除部门失败!");
        }
    }
}
