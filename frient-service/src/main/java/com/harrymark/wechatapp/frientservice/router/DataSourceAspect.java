package com.harrymark.wechatapp.frientservice.router;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源代理
 */
@Aspect
@Component
@Order(1)
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(com.harrymark.wechatapp.frientservice.router.DataSource)")
    public void dsAspect() {
    }

    @Around("dsAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Class targetClass = pjp.getTarget().getClass();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        DataSource dataSource = null;
        if (method.isAnnotationPresent(DataSource.class)) {
            dataSource = method.getAnnotation(DataSource.class);
        } else if (targetClass.isAnnotationPresent(DataSource.class)) {
            dataSource = (DataSource) targetClass.getAnnotation(DataSource.class);
        }
        if (dataSource != null) {
            DynamicDataSourceHolder.setDataSource(dataSource.name());
            logger.info("dataSource has been switched to " + dataSource.name());
        }
        Object result = pjp.proceed();
        if (method.isAnnotationPresent(DataSource.class) || targetClass.isAnnotationPresent(DataSource.class)) {
            DynamicDataSourceHolder.clearDataSource();
        }
        return result;
    }
}
