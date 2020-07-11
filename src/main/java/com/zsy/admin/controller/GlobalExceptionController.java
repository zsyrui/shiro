package com.zsy.admin.controller;

import com.zsy.admin.domian.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: admin
 * @description:
 * @author: 张思远
 * @create: 2020-05-06 21:30
 */
@ControllerAdvice
public class GlobalExceptionController {
    private Logger logger= LoggerFactory.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultError(HttpServletRequest request, Exception e) throws Exception{
        logger.error("",e);
        Result r =new Result();
        r.setMsg(e.getMessage());
        if(e instanceof org.springframework.web.servlet.NoHandlerFoundException){
            r.setCode(404);
        }else{
            r.setCode(500);
        }

        r.setStatus(false);
        r.setData(null);
        //
        return r;
    }
}
