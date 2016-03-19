package zzz.study.threadprogramming.basic.experiment;

import zzz.study.threadprogramming.basic.AtomicLongDemo;

/**
 * Created by lovesqcc on 16-2-15.
 */
public class ConcurrentExperimentExecutor {

    private static final int COUNTS = 10000;

    public ConcurrentExperiment ce;

    public ConcurrentExperimentExecutor() {};

    public ConcurrentExperimentExecutor(ConcurrentExperiment ce) {
        this.ce = ce;
    }

    public void execute() {
        execute(this.ce);
    }

    public void execute(ConcurrentExperiment ce) {
        checkExperiment(ce);

        int count = 0;
        for (int i=0; i<COUNTS;i++) {
            if (!ce.perform()) {
                count++;
            }
        }
        System.out.println("failed: " + count);
    }

    private void checkExperiment(ConcurrentExperiment ce) {
        if (ce == null) {
            throw new NullPointerException("No Concurrent Experiment to execute.");
        }
    }

    public static void main(String[] args) {
       new ConcurrentExperimentExecutor(new AtomicLongDemo()).execute();
    }

}
