package zzz.study.foundations.exception;

public class ExceptionTester {

    public static void main(String[] args) {
        ExceptionTester et = new ExceptionTester();

        try {
            et.f();
        } catch (EatingFishException e) {
            e.printStackTrace(System.out);
        }

        et.g();
    }

    public void f() throws EatingFishException { // 抛出 非RuntimeException 异常，需要指定异常说明
        System.out.println("throw EatingFishException in f() ");
        throw new EatingFishException();
    }

    public void g() { // 抛出 RuntimeException 异常，无需指定异常说明
        System.out.println("throw ChokedException in g() ");
        throw new ChokedException("when eating much food. ");
    }

}

class EatingFishException extends Exception {

    private static final long serialVersionUID = 1L;

}

class ChokedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ChokedException() {
        super();
    }

    public ChokedException(String message) {
        super(message);
    }

}
