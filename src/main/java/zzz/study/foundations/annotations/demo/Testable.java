package zzz.study.foundations.annotations.demo;


public class Testable {

    public void execute() {
        System.out.println("global execute..");
    }

    public void execLocal() {
        System.out.println("local execute..");
    }

    public void execDispatch() {
        System.out.println("dispatch execute..");
    }

    @CustomizedTest(id = 1, desc = "global")
    void testExecute() {
        execute();
    }

    @CustomizedTest(id = 2, desc = "local")
    void testLocal() {
        execLocal();
    }

}
