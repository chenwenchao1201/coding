package com.earl.learn.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @program: shirotest
 * @description: 自定义客户realm
 * @author: earl
 * @create: 2018-10-14 12:56
 **/
public class CustomRealm extends AuthorizingRealm {
    Map<String,String> userMapper = new HashMap<>();
    {
        userMapper.put("earl","123456");
        userMapper.put("admin","admin");
        //设置这个realm的自定义名称
        super.setName("CustomRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName  = (String)principals.getPrimaryPrincipal();
        //获得角色信息
        Set<String> roles = getRolesByUserName(userName);

        //获得权限数据
        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthenticationInfo = new SimpleAuthorizationInfo();
        simpleAuthenticationInfo.setStringPermissions(permissions);
        simpleAuthenticationInfo.setRoles(roles);

        return simpleAuthenticationInfo;
    }
    /** 
    * @Description: 模拟从数据库得到角色信息
    * @Param:  
    * @return:  
    * @Author: earl 
    * @Date: 2018/10/14  16:46
    */ 
    private Set<String> getPermissionsByUserName(String userName){
        Set<String> sets = new HashSet<>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    private Set<String> getRolesByUserName(String userName){
        Set<String> sets = new HashSet<>();
        sets.add("user");
        sets.add("admin");
        return sets;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从主体得到的用户信息
        String userName = (String)token.getPrincipal();

        //通过username去数据库拿凭证（密码）
        String password = getPassWordByUserName(userName);
        if(password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,password,"customRealm");

        return authenticationInfo;
    }
    /** 
    * @Description:  模拟从数据库取数据的过程
    * @Param:  
    * @return:  
    * @Author: earl 
    * @Date: 2018/10/14  13:21
    */ 
    private String getPassWordByUserName(String userName){
        return userMapper.get(userName);
    }
}
