package com.earl.learn.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: shirotest
 * @description: shiro 用户验证测试
 * @author: earl
 * @create: 2018-10-12 22:03
 **/
public class iniRealmTest {
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
        IniRealm iniRealm = new IniRealm("classpath:user.ini");
        //默认securityManager创建，并把realm给赋值
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(iniRealm);
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
