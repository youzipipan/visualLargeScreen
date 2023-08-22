package com.pg.screen.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

/**
 * 入参出参AOP
 *
 * @author c.chuang
 */
@Component
@Aspect
@Slf4j
public class AspectControllerTimeConsuming {

    @Around("within(com.pg.screen.controller.*)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 获得方法名
        String methodName = pjp.getSignature().getName();
        LocalTime beginTime = LocalTime.now();
        // 执行目标方法
        Object result = pjp.proceed();
        LocalTime endTime = LocalTime.now();
        Duration duration = Duration.between(beginTime, endTime);
        log.info("{}()方法的入参为：{}，返回值为：{}，耗时(ms):{}",
                methodName,
                pjp.getArgs(),
                result,
                duration.getNano() / 1000000);
        return result;
    }

}
