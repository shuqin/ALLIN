package zzz.study.foundations.exception;

public class ReThrowException extends Exception {

    private static final long serialVersionUID = 1L;

    public ReThrowException(String msg) {
        super(msg);
    }

    public static void f() throws Exception {

        try {
            System.out.println("try-block in f(), Throw Exception");
            throw new Exception("Originated Exception");
        } catch (Exception e) {
            System.out.println("caught in f(): " + e.getMessage());
            e.printStackTrace(System.out);
            System.out.println("rethrown Exception in f()");
            throw e;
        }
    }

    public static void g() throws Exception {

        try {
            System.out.println("try-block in g(), Throw Exception");
            throw new Exception("Originated Exception");
        } catch (Exception e) {
            System.out.println("caught in g(): " + e.getMessage());
            e.printStackTrace(System.out);
            System.out.println("rethrown Exception in g()");
            throw (Exception) e.fillInStackTrace();
        }

    }

    public static void h() throws Exception {

        try {
            System.out.println("try-block in h(), Throw Exception");
            throw new ReThrowException("ReThrowException");
        } catch (Exception e) {
            System.out.println("caught in h(): " + e.getMessage());
            e.printStackTrace(System.out);
            System.out.println("rethrow Exception in h()");
            throw e;
        }
    }

    public static void t() throws ReThrowException {

        try {
            System.out.println("try-block in t(), Exception");
            throw new EatingFishException();
        } catch (EatingFishException e) {
            System.out.println("caught in t(): " + e.getMessage());
            e.printStackTrace(System.out);
            System.out.println("rethrow Exception in t()");
            throw new ReThrowException("ReThrowException");
        }
    }

    public static void main(String[] args) {

        System.out.println("************** 重抛异常(一) ****************");

        try {
            System.out.println("try-block in main");
            f();
        } catch (Exception e) {
            System.out.println("caught in main[1]: " + e.getMessage());
            e.printStackTrace(System.out);
        }


        System.out.println("************** 重抛异常(二) ****************");

        try {
            System.out.println("try-block in main");
            g();
        } catch (Exception e) {
            System.out.println("caught in main[2]: " + e.getMessage());
            e.printStackTrace(System.out);
        }

        System.out.println("************** 重抛异常(三) ****************");

        try {
            System.out.println("try-block in main");
            h();
        } catch (Exception e) {
            System.out.println("caught in main[3]: " + e.getMessage());
            e.printStackTrace(System.out);
        }

        System.out.println("************** 重抛异常(四) ****************");

        try {
            System.out.println("try-block in main");
            t();
        } catch (ReThrowException e) {
            System.out.println("caught in main[4]: " + e.getMessage());
            e.printStackTrace(System.out);
        }

    }

}
