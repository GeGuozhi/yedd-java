package com.ggz.server.service.impl;

import com.ggz.server.pojo.RespBean;
import com.ggz.server.service.SystemService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;

/**
 * 系统配置impl
 *
 * @author ggz on 2022/2/28
 */
@Service
@PropertySource("classpath:config/system.conf")
public class SystemServiceImpl implements SystemService {
    @Value("${initScriptPath}")
    private String initSqlScript;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public RespBean initDatabase() {

        Runnable runnable = () -> {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            Connection conn = sqlSession.getConnection();
            ClassPathResource rc = new ClassPathResource("sqlScript/yeb_initData.sql");
            EncodedResource er = new EncodedResource(rc, "utf-8");
            ScriptUtils.executeSqlScript(conn, er);
        };
        runnable.run();
        return RespBean.success("初始化中...");
    }
}