package cc.lovesq.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class TimecostAspect {

    private static Logger logger = LoggerFactory.getLogger(TimecostAspect.class);

    @Pointcut(value = "@annotation(cc.lovesq.annotations.Timecost)")
    public void timecostAspect() {
    }

    @Around(value = "@annotation(cc.lovesq.annotations.Timecost)")
    public Object around(ProceedingJoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        String methodInfo = "[" + method.getName() + "][" + Arrays.deepToString(args) + "]";

        long start = System.nanoTime();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("failed to run method");
        }
        long end = System.nanoTime();
        logger.info(methodInfo + "[cost]: {} us", (end-start)/1000 );

        return obj;
    }

}
