package com.earl.learn.test;

import com.earl.learn.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @program: shirotest
 * @description: 自定义realm测试类
 * @author: earl
 * @create: 2018-10-14 13:40
 **/
public class CustomRealmTest {

    @Test
    public void testAuthentication(){
        //创建自定义的remls
        CustomRealm customRealm = new CustomRealm();

        //默认securityManager创建，并把realm给赋值
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(customRealm);

        //把manager给util类，并通过util获得subject对象
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        //构建一个输入的账号密码，并传给subject去验证
        UsernamePasswordToken token = new UsernamePasswordToken("earl","123456");

        subject.login(token);
        System.out.println("是否认证"+subject.isAuthenticated());

        subject.checkRoles("user");
        subject.checkPermissions("user:delete");

        subject.logout();
        System.out.println("是否认证"+subject.isAuthenticated());
    }
}
