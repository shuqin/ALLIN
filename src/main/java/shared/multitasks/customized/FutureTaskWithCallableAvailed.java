package shared.multitasks.customized;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskWithCallableAvailed<T> extends FutureTask<T> {

    private Callable<T> task;

    public FutureTaskWithCallableAvailed(Callable<T> task) {
        super(task);
        this.task = task;
    }

    public Callable<T> getTask() {
        return task;
    }

}
