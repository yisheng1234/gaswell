package com.gaswell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
* 自定义一个返回类型，用于给前台返回信息
* */
public class Result {
    private boolean success;
    private  int code;
    private String msg;
    private Object data;
    private Integer totalData;
    private Integer totalPage;

    public static Result success(Object data){
        return new Result(true,200,"success",data,null,null);
    }

    public static Result success(Object data, String msg){
        return new Result(true,200,msg,data,null,null);
    }

    public static Result fail(int code, String msg){
        return new Result(false,code,msg,null,null,null);
    }
}
