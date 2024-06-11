package com.sctech.emailapp.aspect;

import com.sctech.emailapp.service.AuditLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogService auditLogService;

    @AfterReturning(value = "@annotation(com.sctech.emailapp.annotation.Auditable)", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String description = methodName + " executed successfully";
        auditLogService.logEvent("METHOD_EXECUTION_SUCCESS", description);
    }

    @AfterThrowing(value = "@annotation(com.sctech.emailapp.annotation.Auditable)", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        String description = methodName + " failed with exception: " + exception.getMessage();
        auditLogService.logEvent("METHOD_EXECUTION_FAILURE", description);
    }

    @Around(value = "@annotation(com.sctech.emailapp.annotation.Auditable)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            return result;
        } catch (AccessDeniedException ex) {
            String methodName = joinPoint.getSignature().getName();
            String description = methodName + " unauthorized access: " + ex.getMessage();
            auditLogService.logEvent("METHOD_EXECUTION_UNAUTHORIZED", description);
            throw ex;
        } catch (Throwable ex) {
            throw ex;
        }
    }

}