package com.example.springbootdemo.common.valid;

import com.example.springbootdemo.common.exception.BusinessException;

public class ConfirmFiled {
    public static void isTrue(boolean expression, String msg) {
        if(!expression) {
            throw new BusinessException(msg);
        }
    }
}
