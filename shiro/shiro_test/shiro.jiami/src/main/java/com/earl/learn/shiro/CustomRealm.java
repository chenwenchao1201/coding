package com.earl.learn.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

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
        //加密后的密码
        userMapper.put("earl","0704bd91c825b669f95bafddb043b544");
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
        //加盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("earl"));
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

    public static void main(String[] args) {
        //生成加盐的加密密码
        Md5Hash md5Hash  = new Md5Hash("123456","earl");
        System.out.println(md5Hash.toString());
    }
}
