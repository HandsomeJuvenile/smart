package com.ace.smart.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {
    ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.ace.smart.common.controller..*.*(..))")
    public void webLog(){
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());

        // 接收到请求 记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 日志记录
        LOGGER.info("请求地址:"+request.getRequestURI().toString());
        LOGGER.info("请求方式:"+request.getMethod());
        LOGGER.info("IP:" + request.getRemoteAddr());
        LOGGER.info("请求方法"+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        LOGGER.info("请求参数:"+ Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
        LOGGER.info("耗时(毫秒):"+(System.currentTimeMillis()-startTime.get()));
    }
}
