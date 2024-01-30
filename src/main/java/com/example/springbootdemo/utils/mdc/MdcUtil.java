package com.example.springbootdemo.utils.mdc;

import org.slf4j.MDC;
import java.util.Map;
import java.util.UUID;

/**
 * MDC工具类
 * @author liushuai
 * @date 2024.01.29
 * @explain  此工具类定义了一个traceId作为日志的key，使用UUID为不同的请求生成不同的traceId值，
 * 作为value, 另外定义了一个spanId区分不同的线程。
 */
public class MdcUtil {

    public static final String TRACE_ID = "traceId";

    public static final String SPAN_ID = "spanId";

    public static String generateUuId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void setContextMap(Map<String, String> context) {
        MDC.setContextMap(context);
    }

    public static void removeTraceId() {
        MDC.remove(TRACE_ID);
        MDC.remove(SPAN_ID);
    }

    public static void clear() {
        MDC.clear();
    }

    // 每个线程都会产生一个属于自己的ID
    public static void initSpanId() {
        MDC.put(SPAN_ID, generateUuId());
    }
}
