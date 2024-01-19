package com.epam.esm.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Aspect for logging method calls and exceptions thrown by methods in Spring components.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut for Spring components.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)" +
            " || within(@org.springframework.web.bind.annotation.ControllerAdvice *)")
    public void loggingTargets() {
    }

    /**
     * Log method execution before it starts.
     */
    @Before("loggingTargets()")
    public void logBeforeExecution(JoinPoint joinPoint) {
        LOGGER.info("Executing method: {}.{} with arguments {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.asList(joinPoint.getArgs()));
    }

    /**
     * Log method execution time.
     */
    @Around("loggingTargets()")
    public Object logDuringExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.nanoTime();
        Object proceed = proceedingJoinPoint.proceed();
        long end = System.nanoTime();
        LOGGER.info("Execution of {}.{} took {} milliseconds",
                proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName(),
                TimeUnit.NANOSECONDS.toMillis(end - start));
        return proceed;
    }

    /**
     * Log method execution after it returns a value.
     */
    @AfterReturning(value = "loggingTargets()", returning = "value")
    public void logAfterExecution(JoinPoint joinPoint, Object value) {
        LOGGER.info("Method {}.{} executed and returned value [{}]",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                value);
    }

    /**
     * Log exceptions thrown by methods.
     */
    @AfterThrowing(value = "loggingTargets()", throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Exception exception) {
        LOGGER.error("Method {}.{} threw {}. Exception: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }
}

