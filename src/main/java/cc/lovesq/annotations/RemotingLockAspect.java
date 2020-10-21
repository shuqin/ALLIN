package cc.lovesq.annotations;

import cc.lovesq.components.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

@Component
@Scope
public class RemotingLockAspect {

    @Resource(name = "redissonLock")
    private DistributedLock distributedLock;

    @Pointcut("@annotation(cc.lovesq.annotations.RemotingLock)")
    public void lockAspect() {

    }

    @Around("lockAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        Object obj;
        String lockKey = "";

        Object[] args = joinPoint.getArgs();
        RemotingLock remotingLock = joinPoint.getTarget().getClass().getAnnotation(RemotingLock.class);
        if (args.length >= 1) {
            lockKey = (String) args[0];
        }
        else {
            throw new IllegalArgumentException("Lock Annotation should provider one arg ");
        }

        Lock lock = distributedLock.lock(lockKey, remotingLock.time(), remotingLock.timeUnit());
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
