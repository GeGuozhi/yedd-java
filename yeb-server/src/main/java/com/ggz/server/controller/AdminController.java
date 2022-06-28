package com.ggz.server.controller;


import com.ggz.server.pojo.Admin;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.pojo.Role;
import com.ggz.server.service.IAdminService;
import com.ggz.server.service.IRoleService;
import com.ggz.server.utils.FastDFSUtils;
import com.ggz.server.utils.RedisDistributedLockUtils;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ggz
 * @since 2021-12-15
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    private final IAdminService iAdminService;

    private final IRoleService iRoleService;

    private final RedisDistributedLockUtils redisDistributedLockUtils;

    private final RedisTemplate redisTemplate;

    static ExecutorService executorService = Executors.newFixedThreadPool(1000);

    public AdminController(IAdminService iAdminService, IRoleService iRoleService, RedisDistributedLockUtils redisDistributedLockUtils, RedisTemplate redisTemplate) {
        this.iAdminService = iAdminService;
        this.iRoleService = iRoleService;
        this.redisDistributedLockUtils = redisDistributedLockUtils;
        this.redisTemplate = redisTemplate;
    }

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/getAllAdmins")
    public List<Admin> getAllAdmins(String keywords) {
        return iAdminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping("updateAdmin")
    public RespBean updateAdmin(@RequestBody Admin admin) {
        if (iAdminService.updateById(admin)) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/deleteAdmin/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {
        if (iAdminService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("updateAdminRole")
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        return iAdminService.updateAdminRole(adminId, rids);
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("roles")
    public List<Role> roles() {
        return iRoleService.list();
    }

    @ApiOperation(value = "用户中心更新密码")
    @PutMapping("/info/updatePassword")
    public RespBean updatePassword(@RequestBody Map<String, Object> passwordMap, Authentication authentication) {
        String oldPassword = String.valueOf(passwordMap.get("oldPassword"));
        String password = String.valueOf(passwordMap.get("pass"));
        Integer adminId = (Integer) passwordMap.get("adminId");
        return iAdminService.updatePasswordForUserCenter(adminId, oldPassword, password);
    }

    @ApiOperation(value = "用户中心更新操作员")
    @PutMapping("/info/updateAdminForUserCenter")
    public RespBean updateAdminForUserCenter(@RequestBody Admin admin, Authentication authentication) {
        if (iAdminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "用户中心更新头像")
    @PostMapping("/info/updatePictureForUserCenter")
    public RespBean updatePictureForUserCenter(MultipartFile file, Integer id, Authentication authentication) {
        String[] filePath = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl() + filePath[0] + "/" + filePath[1];
        return iAdminService.updatePictureForUserCenter(url, id, authentication);
    }

    @ApiOperation(value = "ceshi")
    @PostMapping("/test")
    public void test() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                execBusinessMethod();
            }
        });

        for (int i = 0; i < 100; i++) {
            Thread thread1 = new Thread(thread, "thread_" + i);
            executorService.execute(thread1);
        }
    }

    /**
     * 业务方法
     */
    private static AtomicInteger key = new AtomicInteger();
    private static int a = 0;
    public static void businessMethod(){
        int key = a++;
        if(a>30){
            System.out.println("很抱歉，票没啦!");
        }else{
            System.out.println("抢票程序：" + Thread.currentThread().getName()
                    + "在" + LocalDateTime.now() + "抢到了第" + key +"张票！");
        }
    }

    /**
     * 调用业务方法的地方
     * @throws InterruptedException
     */
    public void execBusinessMethod() throws InterruptedException {
        String key = /*AdminController.class.getName()+*/"test";
        String value = UUID.randomUUID().toString();
        Boolean lockResult = redisDistributedLockUtils.lock(key,value);
        //判断是否获取到redis分布式锁，获取到执行业务方法，获取不到等待10s后重试（根据业务修改时间）
        if(lockResult){
            //不允许重复的业务方法
            businessMethod();
        }else{
            Thread.sleep(100);
            execBusinessMethod();
        }
        redisDistributedLockUtils.unlock(key,value);
    }



}
