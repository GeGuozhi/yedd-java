package com.ggz.server.controller;


import com.ggz.server.pojo.RespBean;
import com.ggz.server.pojo.Salary;
import com.ggz.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/salary/sob/")
public class SalaryController {
    @Autowired
    ISalaryService salaryService;

    @ApiOperation(value = "获取所有员工工资帐套")
    @GetMapping(value = "getAllSalary")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation(value = "添加工资帐套")
    @PostMapping(value = "addSalary")
    public RespBean addSalary(@RequestBody Salary salary) {
        salary.setCreateDate(LocalDateTime.now());
        if (salaryService.save(salary)) {
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "删除工资帐套")
    @DeleteMapping(value = "/delSalary/{id}")
    public RespBean delSalary(@PathVariable Integer id) {
        if (salaryService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }


    @ApiOperation(value = "更新工资帐套")
    @PutMapping(value = "/updateSalary")
    public RespBean updateSalary(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

}
