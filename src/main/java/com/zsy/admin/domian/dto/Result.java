package com.zsy.admin.domian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: admin
 * @description:
 * @author: 张思远
 * @create: 2020-05-06 20:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String msg;
    private T data;
    private Boolean status;
    private Integer code;

    private void setOk(String msg,T data,Boolean status,Integer code){
        this.msg=msg;
        this.data=data;
        this.code=code;
        this.status=status;
    }
    public static Result getOk(String msg,Object data,Boolean status,Integer code){
        Result result=new Result();
        result.setOk(msg,data,status,code);
        return result;
    }
}
