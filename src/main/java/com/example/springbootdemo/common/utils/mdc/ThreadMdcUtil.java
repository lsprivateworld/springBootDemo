package com.example.springbootdemo.common.utils.mdc;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 新开线程或线程池中线程的MDC上下文无法获取到主线程中的traceId，需要重写，
 * 再创建新线程或者线程池启动新线程之前，复制主线程的 traceId，并创建新线程的spanId
 */
public class ThreadMdcUtil {

    public static void setTraceId(String traceId) {
        MdcUtil.setTraceId(traceId);
        MdcUtil.initSpanId();
    }

    public static void resetTraceId() {
        MdcUtil.setTraceId(MdcUtil.generateUuId());
        MdcUtil.initSpanId();
    }

    public static void setTraceIdIfAbsent() {
        if (MdcUtil.getTraceId() == null) {
            MdcUtil.setTraceId(MdcUtil.generateUuId());
        }
        MdcUtil.initSpanId();
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MdcUtil.clear();
            } else {
                MdcUtil.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MdcUtil.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MdcUtil.clear();
            } else {
                MdcUtil.setContextMap(context);
            }
            //设置traceId
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MdcUtil.clear();
            }
        };
    }
}
