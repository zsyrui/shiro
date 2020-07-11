package com.zsy.admin.shiro;

import com.zsy.admin.domian.po.User;
import com.zsy.admin.service.UserService;
import com.zsy.admin.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;



@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;


    //权限管理
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    //身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user= userService.login(username);
        if(user==null) {
            throw new AuthenticationException("不存在该用户");
        }else if(user.getPassword().equals( new String(token.getPassword())  )){

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
            return info;
        }
        throw new AuthenticationException("用户名或密码错误");

    }

    //token验证


}

