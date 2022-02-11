package com.xxxx.server.controller;


import com.xxxx.server.pojo.*;
import com.xxxx.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
@RequestMapping("/employee/basic/")
public class EmployeeController {

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IPoliticsStatusService iPoliticsStatusService;

    @Autowired
    private IJoblevelService iJoblevelService;

    @Autowired
    private INationService iNationService;

    @Autowired
    private IPositionService iPositionService;

    @Autowired
    private IDepartmentService iDepartmentService;

    @ApiOperation(value = "获取所有员工(分页)")
    @GetMapping("/getEmployee")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Employee employee, LocalDate[] beginDateScope) {

        return iEmployeeService.getEmployeeByPage(currentPage, size, employee, beginDateScope);
    }

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/getAllPoliticsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return iPoliticsStatusService.list();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/getAllJoblevels")
    public List<Joblevel> getAllJoblevels() {
        return iJoblevelService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/getAllNations")
    public List<Nation> getAllNations() {
        return iNationService.list();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/getAllPositions")
    public List<Position> getAllPositions() {
        return iPositionService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/getAllDeps")
    public List<Department> getAllDeps() {
        return iDepartmentService.getAllDepartments(-1);
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkId")
    public RespBean maxWorkId() {
        return iEmployeeService.maxWorkId();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/addEmployee")
    public RespBean addEmployee(@RequestBody Employee employee) {
        return iEmployeeService.addEmployee(employee);
    }

    @ApiOperation(value = "更新员工")
    @PostMapping("/updateEmployee")
    public RespBean updateEmployee(@RequestBody Employee employee) {
        if (iEmployeeService.updateById(employee)) {
            return RespBean.success("更新成功！");
        }
        return RespBean.success("更新失败！");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/deleteEmployee/{id}")
    public RespBean deleteEmployee(@PathVariable Integer id) {
        if (iEmployeeService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.success("删除失败！");
    }

}
