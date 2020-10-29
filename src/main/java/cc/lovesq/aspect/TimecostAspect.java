package cc.lovesq.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class TimecostAspect {

    private static Log log = LogFactory.getLog(TimecostAspect.class);

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
            log.error("failed to run method");
        }
        long end = System.nanoTime();
        log.info(methodInfo + "[cost]: " + (end-start)/1000 + " us");

        return obj;
    }

}
