package com.zsy.admin.controller;

import com.zsy.admin.domian.dto.Result;
import com.zsy.admin.domian.po.User;
import com.zsy.admin.service.UserService;
import com.zsy.admin.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @program: admin
 * @description:
 * @author: 张思远
 * @create: 2020-05-05 15:32
 */
@Slf4j
@Api
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    @PostMapping(value = "/login")
    @ApiOperation(value = "登入请求",notes = "登入")
    public Result Login(String username, String password, HttpServletRequest request){
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        User user = userService.login(username, password);
        SecurityUtils.getSubject().login(token);

//        //更新登录信息
//        user.setIp(HttpTool.getIpAddr(request));
//        user.setOs(HttpTool.getOs(request));
//        user.setUpdateUserId(user.getId());
//        user.setUpdateTime(CommonTool.getTimestamp());

        //设置session时间
        //SecurityUtils.getSubject().getSession().setTimeout(1000*60*30);

        //token信息
        Subject subject = SecurityUtils.getSubject();
        Serializable tokenId = subject.getSession().getId();
        return Result.getOk("登录成功", tokenId,true,200 );
    }
    @GetMapping(value = "/loginout")
    @ApiOperation(value = "登出请求",notes = "登出")
    public Result LoginOut(){
        SecurityUtils.getSubject().logout();
        return Result.getOk("登出成功", null,true,200 );
    }



}
