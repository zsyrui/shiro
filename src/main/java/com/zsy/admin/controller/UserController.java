package com.zsy.admin.controller;

import com.zsy.admin.domian.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: admin
 * @description:
 * @author: 张思远
 * @create: 2020-05-07 18:32
 */

@Slf4j
@Api
@RestController
public class UserController {

    @PostMapping(value = "/test1")
    @ApiOperation(value = "测试请求1",notes = "测试")
    public Result test1(){
        Result result=new Result();
        result.setMsg("成功");
        return result;
    }
}
