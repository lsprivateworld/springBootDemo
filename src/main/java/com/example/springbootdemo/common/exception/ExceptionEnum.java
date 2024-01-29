package com.example.springbootdemo.common.exception;

public class ExceptionEnum {

    //此处用于自定义异常的命名及对应状态码和提示信息的设置
//    EXCEPTION_ONE(401,"异常一提示信息"),
//    EXCEPTION_TWO(402,"异常二提示信息"),
//    EXCEPTION_THREE(403,"异常三提示信息");

    //状态码
    private Integer code;
    //异常提示信息
    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
