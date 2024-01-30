package com.example.springbootdemo.common.result;

import com.example.springbootdemo.domin.Article;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = -4128965022554205426L;

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;

    private int code;
    private String msg = StringUtils.EMPTY;
    private T data;

    public JsonResult() {
    }

    public JsonResult(int code, T data) {
        this.code = code;
        this.data = data;
    }
    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public JsonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(T data) {
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(data);
    }

    public static <T> JsonResult<T> success(Article article) {
        return new JsonResult();
    }

    public static <T> JsonResult<T> success(int code, T data) {
        return new JsonResult(code, data);
    }

    public static <T> JsonResult<T> error(int code, String msg, T data) {
        return new JsonResult(code, msg);
    }
    public static <T> JsonResult<T> error(String msg) {
        return new JsonResult(ERROR, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
