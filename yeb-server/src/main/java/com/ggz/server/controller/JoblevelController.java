package com.ggz.server.controller;


import com.ggz.server.pojo.Joblevel;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@RestController
@RequestMapping("/system/basic/")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;


    @ApiOperation(value = "获取所有职称信息")
    @GetMapping(value = "/getAllJoblevels")
    public List<Joblevel> getAllJoblevels(){
        return joblevelService.list();
    }

    @ApiOperation(value = "添加职称信息")
    @PostMapping(value = "/addJoblevel")
    public RespBean addJoblevel(@RequestBody Joblevel Joblevel){
        Joblevel.setCreateDate(LocalDateTime.now());
        if(joblevelService.save(Joblevel)){
            return RespBean.success("添加成功！");
        }else{
            return RespBean.error("添加失败!");
        }
    }

    @ApiOperation(value = "更新职称信息")
    @PutMapping(value = "/updateJoblevel")
    public RespBean updateJoblevel(@RequestBody Joblevel Joblevel){
        if(joblevelService.updateById(Joblevel)){
            return RespBean.success("更新成功！");
        }else{
            return RespBean.error("更新失败!");
        }
    }

    @ApiOperation(value = "删除职称信息")
    @DeleteMapping(value = "/delJoblevel/{id}")
    public RespBean delJoblevel(@PathVariable Integer id){
        if(joblevelService.removeById(id)){
            return RespBean.success("删除成功！");
        }else{
            return RespBean.error("删除失败!");
        }
    }


    @ApiOperation(value = "批量删除职称信息")
    @DeleteMapping(value = "/delJoblevelsByIds")
    public RespBean delJoblevelsByIds(Integer[] ids){
        if(joblevelService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功！");
        }else{
            return RespBean.error("删除失败!");
        }
    }


}
