package com.example.springbootdemo.common.aspect;

import com.alibaba.fastjson.JSON;
import com.example.springbootdemo.common.annotation.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志切面
 * @author liushuai
 * @date 2024/01/30
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

    private static final String REQUEST_INFO =
            "------Request Info------";
    private static final String REQUEST_END =
            "------Request End------";

    private boolean openRequestLog = true;
    private boolean openRequestExceptionLog = true;

    private static final Logger log = LoggerFactory.getLogger("myLog");

    private static void outLog(Map<String, String> m, String url, String methodName) {
        log.info("操作内容:{}", m.get("actionName"));
        log.info("请求URL:{}", url);
        log.info("请求类名:{}", m.get("className"));
        log.info("请求方法:{}", methodName);
        log.info("请求参数:{}", JSON.toJSONString(m.get("methodParams")));
    }

    private static Map<String, String> getControllerMethodDescription(JoinPoint joinPoint)
            throws ClassNotFoundException {
        Map<String, String> map = new HashMap<>();
        if (null == joinPoint) {
            return map;
        }
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        List<Object> args = Arrays.asList(arguments);
        map.put("methodParams", args.toString());
        Class<?> targetClass = Class.forName(targetName);
        map.put("className", targetClass.getName());
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazz = method.getParameterTypes();
                if (clazz.length == arguments.length) {
                    map.put("actionName", method.getAnnotation(LogAnnotation.class).value());
                    break;
                }
            }
        }
        return map;
    }

    @Around("@annotation(com.example.springbootdemo.common.annotation.LogAnnotation)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(REQUEST_INFO);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes requestAttributes1 = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = requestAttributes1.getRequest();
            Object result1 = null;
            try {
                if (openRequestLog) {
                    Map<String, String> m = getControllerMethodDescription(joinPoint);
                    String url = request.getRequestURI();
                    String methodName = joinPoint.getSignature().getName();
                    //输出日志
                    outLog(m, url, methodName);
                }
                result1 = joinPoint.proceed();
                log.info("请求结果:{}", JSON.toJSONString(result1));
                log.info(REQUEST_END);
            } catch (Throwable e) {
                if (openRequestLog && openRequestExceptionLog) {
                    log.error("异常信息:{}", e.getMessage(), e);
                    log.info(REQUEST_END);
                    throw e;
                } else if (!openRequestLog && openRequestExceptionLog) {
                    //没有开启请求日志并且开启了请求请求异常日志，则打印相关请求信息
                    Map<String, String> m = getControllerMethodDescription(joinPoint);
                    String url = request.getRequestURI();
                    String methodName = joinPoint.getSignature().getName();
                    outLog(m, url, methodName);
//                    log.info("请求用户:{}", UserUtil.getUser().getId());
                    log.info(REQUEST_END);
                    log.error("异常信息:{}", e.getMessage(), e);
//                    return ResponseVo.errResult(ApiResponseEnum.SYSTEM_ERROR.getValue());
                    throw e;
                } else {
                    throw e;
                }
            }
            return result1;
        }
        return joinPoint.proceed();
    }

}