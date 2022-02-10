package com.xxxx.server.controller;


import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService iDepartmentService;

    @ApiOperation("获取所有部门")
    @GetMapping(value = "/getAllDepartments")
    public List<Department> getAllDepartments(){
        return iDepartmentService.getAllDepartments(-1);
    }

    @ApiOperation("添加部门")
    @PostMapping(value = "/addDep")
    public RespBean addDep(@RequestBody Department department){
        return iDepartmentService.addDep(department);
    }

    @ApiOperation("删除部门")
    @DeleteMapping(value = "/deleteDep/{id}")
    public RespBean deleteDep(@PathVariable Integer id){
        return iDepartmentService.deleteDep(id);
    }
}
