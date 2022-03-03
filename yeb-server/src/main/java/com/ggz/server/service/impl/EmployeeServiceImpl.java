package com.ggz.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggz.mail.MailConstants;
import com.ggz.server.mapper.EmployeeMapper;
import com.ggz.server.mapper.MailLogMapper;
import com.ggz.server.pojo.Employee;
import com.ggz.server.pojo.MailLog;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.pojo.RespPageBean;
import com.ggz.server.service.IEmployeeService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;

    /**
     * 获取所有员工（分页）
     *
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     */
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> lists = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(lists.getTotal(), lists.getRecords());
        return respPageBean;
    }

    /**
     * 获取工号
     *
     * @return
     */
    @Override
    public RespBean maxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workId)"));
        return RespBean.success(null, String.format("%08d", Integer.parseInt(maps.get(0).get("max(workId)").toString()) + 1));
    }

    @Override
    public RespBean addEmployee(Employee employee) {
        //设置最大工号+1
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workId)"));
        String workId = String.format("%08d", Integer.parseInt(maps.get(0).get("max(workId)").toString()) + 1);
        employee.setWorkID(workId);

        //处理合同期限，保留两位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        Long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365)));
        if (1 == employeeMapper.insert(employee)) {
            //数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();

            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(MailConstants.DELIVERING);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);
            //发送信息
            Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp,
                    new CorrelationData(msgId));
            return RespBean.success("添加成功！");
        }
        return RespBean.success("添加失败！");
    }

    /**
     * 查询员工
     *
     * @param id
     * @return
     */
    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    @Override
    public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> lists = employeeMapper.getEmployeeWithSalary(page);
        RespPageBean respPageBean = new RespPageBean(lists.getTotal(), lists.getRecords());
        return respPageBean;
    }
}
