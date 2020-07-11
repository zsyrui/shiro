package com.zsy.admin.controller;

import com.zsy.admin.domian.dto.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: admin
 * @description:
 * @author: 张思远
 * @create: 2020-05-08 11:12
 */
@RestController
@RequestMapping("/shiroError")
public class ShiroController {
    @GetMapping(value = "/login")
    @ApiOperation(value = "未登录接口",notes = "未登录")
    public Result login(){
        Result result=new Result();
        result.setData(null);
        result.setCode(2000);
        result.setStatus(false);
        result.setMsg("未登录");
        return result;
    }
    @GetMapping(value = "/unauthorized")
    @ApiOperation(value = "未授权接口",notes = "未授权")
    public Result unauthorized(){
        Result result=new Result();
        result.setData(null);
        result.setCode(3000);
        result.setStatus(false);
        result.setMsg("未授权");
        return result;
    }
}
