package com.earl.learn.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @program: shirotest
 * @description: shiro 用户验证测试
 * @author: earl
 * @create: 2018-10-12 22:03
 **/
public class jdbcRealmTest {
    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql//172.17.13.58:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }
   /**
   * @Description: 測試驗證用戶
   * @Param:  
   * @return:  
   * @Author: earl 
   * @Date: 2018/10/12  22:18
   */ 
    @Test
    public void testAuthentication(){
        //创建realm
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //默认不给查去权限，要手动开启
        jdbcRealm.setPermissionsLookupEnabled(true);

        //把我们自己写的sql赋值给jdbcrealm
        String sql = "select password from test_user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        //吧我们自己写的sql去替换jdbcrealm中的默认rolesql
        String rolesql  = "select role_name from test_user_role where user_name = ?";
        jdbcRealm.setUserRolesQuery(rolesql);

        //默认securityManager创建，并把realm给赋值
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(jdbcRealm);

        //把manager给util类，并通过util获得subject对象
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        //构建一个输入的账号密码，并传给subject去验证
        UsernamePasswordToken token = new UsernamePasswordToken("Earl","123456");

        subject.login(token);
        System.out.println("是否认证"+subject.isAuthenticated());

        subject.checkRoles("admin");
        subject.checkPermission("user:delete");

        subject.logout();
        System.out.println("是否认证"+subject.isAuthenticated());
    }
}
