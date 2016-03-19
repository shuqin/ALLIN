package cc.lovesq.study.curry

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class HappyEverySecond {

    static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    static void main(args) {
        every.curry(sec).curry(happy)
        scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS)
    }

    static happy = {
        println("happy")
    }

    static every = {
        sec, runnable ->
            scheduledExecutorService.scheduleAtFixedRate(runnable, 0l, sec, TimeUnit.MILLISECONDS)
    }

    static sec = {
        1000l
    }
}
