package com.ggz.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggz.server.pojo.Employee;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

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

    List<Employee> getEmployee(Integer id);
}
