package zzz.study.foundations.exception;

class AnotherException extends Exception {
}

public class ExceptionChain {

    public static void g() throws EatingFishException {
        System.out.println("Throw EatingFishExcepiton in g()");
        throw new EatingFishException();
    }


    public static void f() throws AnotherException {

        try {
            g();
        } catch (EatingFishException e) {
            System.out.println("Caught in f()");
            e.printStackTrace(System.out);
            AnotherException ae = new AnotherException();
            ae.initCause(e);
            throw ae;
        }
    }

    public static void main(String[] args) {

        try {
            f();
        } catch (AnotherException ae) {
            System.out.println("Caught in main()");
            ae.printStackTrace(System.out);
        } catch (Exception e) {
            System.out.println("Caught in main()");
            e.printStackTrace(System.out);
        }
    }

}

/*
 * Note:
 *
 * 异常链：
 * 通过使用 exception.initCause(someException)方法，
 * 将异常对象someException 的详细信息保存在异常对象exception中供调用。
 *
 */
