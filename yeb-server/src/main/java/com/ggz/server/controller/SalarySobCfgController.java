package com.ggz.server.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ggz.server.pojo.Employee;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.pojo.RespPageBean;
import com.ggz.server.pojo.Salary;
import com.ggz.server.service.IEmployeeService;
import com.ggz.server.service.ISalaryService;
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
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;


    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> salaries() {
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有员工帐套")
    @GetMapping("/getEmployeeWithSalary")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size) {

        return employeeService.getEmployeeWithSalary(currentPage, size);
    }

    @ApiOperation(value = "更新员工帐套")
    @PutMapping("/updateEmployeeWithSalary")
    public RespBean updateEmployeeWithSalary(Integer empId, Integer salaryId) {
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId", salaryId).eq("id", empId))) {
            return RespBean.success("更新成功!");
        }
        return RespBean.success("更新失败！");
    }


}
