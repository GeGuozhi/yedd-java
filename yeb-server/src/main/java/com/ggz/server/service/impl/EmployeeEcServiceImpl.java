package com.ggz.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggz.server.mapper.EmployeeEcMapper;
import com.ggz.server.mapper.EmployeeMapper;
import com.ggz.server.pojo.Employee;
import com.ggz.server.pojo.EmployeeEc;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.pojo.RespPageBean;
import com.ggz.server.service.IEmployeeEcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@Service
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IEmployeeEcService {

}
