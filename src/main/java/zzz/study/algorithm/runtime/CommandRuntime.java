package zzz.study.algorithm.runtime;


public class CommandRuntime {

    private Command command;

    public CommandRuntime(Command command) {
        this.command = command;
    }

    public long runtime() {

        long start = System.currentTimeMillis();
        command.run();
        long end = System.currentTimeMillis();

        return end - start;
    }


}
