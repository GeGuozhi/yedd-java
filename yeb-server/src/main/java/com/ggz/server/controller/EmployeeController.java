package com.ggz.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ggz.server.pojo.*;
import com.ggz.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
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
        return iDepartmentService.list();
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
    @PutMapping("/updateEmployee")
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

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/exportEmployee", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> lists = iEmployeeService.getEmployee(null);
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, lists);
        ServletOutputStream out = null;
        try {
            //流形式
            response.setHeader("content-type", "application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition", "attachment;filename=" +
                    URLEncoder.encode("员工表.xls", "UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "导入员工数据")
    @PostMapping(value = "/importEmployee")
    public RespBean importEmployee(MultipartFile file){
        ImportParams importParams = new ImportParams();
        List<Nation> nations = iNationService.list();
        List<Department> departments = iDepartmentService.list();
        List<PoliticsStatus> politicsStatuses = iPoliticsStatusService.list();
        List<Joblevel> joblevels = iJoblevelService.list();
        List<Position> positions = iPositionService.list();
        HashMap<String,Integer> Map = new HashMap<>();
        nations.forEach(nation->{
            Map.put(nation.getName(),nation.getId());
        });
        departments.forEach(department->{
            Map.put(department.getName(),department.getId());
        });
        politicsStatuses.forEach(politicsStatus->{
            Map.put(politicsStatus.getName(),politicsStatus.getId());
        });
        joblevels.forEach(joblevel->{
            Map.put(joblevel.getName(),joblevel.getId());
        });
        positions.forEach(position->{
            Map.put(position.getName(),position.getId());
        });
        //从第二行开始
        importParams.setTitleRows(1);
        try {
            List<Employee> lists = ExcelImportUtil.importExcel(file.getInputStream(),Employee.class,importParams);
            lists.forEach(employee -> {
                employee.setNationId(Map.get(employee.getNation().getName()));
                employee.setDepartmentId(Map.get(employee.getDepartment().getName()));
                employee.setPoliticId(Map.get(employee.getPoliticsStatus().getName()));
                employee.setJobLevelId(Map.get(employee.getJoblevel().getName()));
                employee.setPosId(Map.get(employee.getPosition().getName()));
            });
            if(iEmployeeService.saveBatch(lists)){
                return RespBean.success("导入成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败！");
    }

}
