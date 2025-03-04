package com.ceylanomer.infrastructure.telemetry;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Aspect for database telemetry
 * This aspect automatically instruments database operations with OpenTelemetry
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseTelemetryAspect {

    private final Tracer tracer;

    /**
     * Pointcut for all repository methods
     */
    @Pointcut("execution(* com.ceylanomer.infrastructure.persistence.repository.*Repository*.*(..))")
    public void repositoryMethods() {
    }

    /**
     * Around advice to create spans for repository methods
     */
    @Around("repositoryMethods()")
    public Object traceRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = method.getName();
        String spanName = className + "." + methodName;
        
        Span span = tracer.spanBuilder(spanName)
                .setSpanKind(SpanKind.CLIENT)
                .setAttribute("db.type", "sql")
                .setAttribute("db.operation", methodName)
                .setAttribute("db.repository", className)
                .startSpan();
        
        try (Scope scope = span.makeCurrent()) {
            // Add parameters as attributes if needed
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null) {
                        span.setAttribute("db.argument." + i, args[i].toString());
                    }
                }
            }
            
            // Execute the repository method
            Object result = joinPoint.proceed();
            
            // Add result information if needed
            if (result != null) {
                span.setAttribute("db.result.type", result.getClass().getSimpleName());
            }
            
            span.setStatus(StatusCode.OK);
            return result;
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR);
            span.recordException(t);
            throw t;
        } finally {
            span.end();
        }
    }
} 