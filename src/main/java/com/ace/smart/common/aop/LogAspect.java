package com.ace.smart.common.aop;

import com.ace.smart.common.annotation.Log;
import com.ace.smart.common.entity.SysLog;
import com.ace.smart.common.service.SysLogService;
import com.ace.smart.common.util.HttpContextUtils;
import com.ace.smart.common.util.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.ace.smart.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()") // 进入切点前 和 切点执行完之后
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = point.proceed();
        long time = System.currentTimeMillis() - startTime;
        savaLog(point,time);
        return result;
}

    private void savaLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log log = method.getAnnotation(Log.class);
        if (sysLog !=null ){
            sysLog.setOperation(log.value());
        }
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className+"."+methodName+"()");
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户名
        sysLog.setTime((int) time);
        // 系统当前时间
        // 保存系统日志
        sysLogService.insert(sysLog);
    }
}
