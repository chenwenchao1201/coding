package com.earl.learn.test;

import com.earl.learn.shiro.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
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

        //创建加密工具,指定加密算法，加密次数
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);

        //把刚才写的加密算法给自定义的realm加上
        customRealm.setCredentialsMatcher(matcher);

        //把manager给util类，并通过util获得subject对象
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        //构建一个输入的账号密码，并传给subject去验证
        UsernamePasswordToken token = new UsernamePasswordToken("earl","123456");

        subject.login(token);
        System.out.println("是否认证"+subject.isAuthenticated());


        subject.logout();
        System.out.println("是否认证"+subject.isAuthenticated());
    }
}
