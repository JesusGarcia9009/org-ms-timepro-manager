package org.ms.timepro.manager.log;


import static org.ms.timepro.manager.utils.ConstantUtil.LOG_END;
import static org.ms.timepro.manager.utils.ConstantUtil.LOG_START;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("@annotation(org.ms.timepro.manager.log.Logger)")
    public void logMethodStart(JoinPoint joinPoint) {
    	log.info(String.format(LOG_START, joinPoint.getSignature().getName()));
    }

    @After("@annotation(org.ms.timepro.manager.log.Logger)")
    public void logMethodEnd(JoinPoint joinPoint) {
    	log.info(String.format(LOG_END, joinPoint.getSignature().getName()));
    }
}
