package cc.lovesq.aspect;

import cc.lovesq.annotations.RemotingLock;
import cc.lovesq.components.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Aspect
@Component
public class RemotingLockAspect {

    @Resource(name = "redissonLock")
    private DistributedLock distributedLock;

    @Pointcut(value = "@annotation(cc.lovesq.annotations.RemotingLock)")
    public void lockAspect() {
    }

    @Around(value = "@annotation(cc.lovesq.annotations.RemotingLock)")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        RemotingLock remotingLock = method.getAnnotation(RemotingLock.class);
        long time = 1;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        if (remotingLock != null) {
            time = remotingLock.time();
            timeUnit = remotingLock.timeUnit();
        }

        String lockKey = "";
        if (args.length >= 1) {
            lockKey = (String) args[0];
        }
        else {
            throw new IllegalArgumentException("Method Annotated by RemotingLock should provider one arg at least");
        }

        Lock lock = distributedLock.lock(lockKey, time, timeUnit);

        Object obj;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException();
        } finally{
            lock.unlock();
        }
        return obj;
    }

}
