package com.ggz.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggz.server.config.security.JwtTokenUtil;
import com.ggz.server.mapper.AdminMapper;
import com.ggz.server.mapper.AdminRoleMapper;
import com.ggz.server.mapper.RoleMapper;
import com.ggz.server.pojo.Admin;
import com.ggz.server.pojo.AdminRole;
import com.ggz.server.pojo.RespBean;
import com.ggz.server.service.IAdminService;
import com.ggz.server.utils.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    //暂时注销
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录返回token
     *
     * @param username
     * @param password
     * @param code
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
//        if (StringUtils.isEmpty(captcha) || !captcha.equals(code)) {
//            return RespBean.error("验证码输入错误请重新输入");
//        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //暂时注销
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确!");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员!");
        }
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.getAdminByUserName(username);
    }

    @Override
    public List<Admin> getAllAdmins(String keywords) {
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(), keywords);
    }

    @Override
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        Integer result = adminRoleMapper.addAdminRole(adminId, rids);
        if (rids.length == result) {
            return RespBean.success("更新操作员角色成功！");
        }
        return RespBean.success("更新操作员角色失败！");
    }

    @Override
    public RespBean updatePasswordForUserCenter(Integer adminId, String oldPassword, String password) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(oldPassword, admin.getPassword())) {
            admin.setPassword(passwordEncoder.encode(password));
            int result = adminMapper.updateById(admin);
            if (1 == result) {
                return RespBean.success("密码修改成功！");
            }
        }
        return RespBean.error("密码修改失败！");
    }

    @Override
    public RespBean updatePictureForUserCenter(String url, Integer id, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        admin.setUserFace(url);
        if (1 == adminMapper.updateById(admin)) {
            ((Admin) authentication.getPrincipal()).setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            return RespBean.success("更新头像成功！", url);
        }
        return RespBean.error("更新头像失败！");
    }

}
