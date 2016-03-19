package zzz.study.threadprogramming.basic.dataconcurrency;

import zzz.study.algorithm.runtime.Command;

public class PrimePrintSingleThreadCommand implements Command {

    private PrimePrintSingleThread ppst;

    public PrimePrintSingleThreadCommand(PrimePrintSingleThread ppst) {
        this.ppst = ppst;
    }

    public void run() {
        ppst.run();
    }


}
