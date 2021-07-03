package zzz.study.threadprogramming.basic.dataconcurrency;

import zzz.study.algorithm.runtime.Command;

public class PrimePrintInConcurrencyCommand implements Command {

    private PrimePrintInConcurrency ppic;

    public PrimePrintInConcurrencyCommand(PrimePrintInConcurrency ppic) {
        this.ppic = ppic;
    }

    public void run() {
        ppic.run();
    }

}
