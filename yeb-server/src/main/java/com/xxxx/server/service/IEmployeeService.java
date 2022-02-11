package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
public interface IEmployeeService extends IService<Employee> {
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    RespBean maxWorkId();

    RespBean addEmployee(Employee employee);
}
