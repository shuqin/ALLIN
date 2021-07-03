package zzz.study.threadprogramming.traps;

public class NoVisibility {

    private boolean isReady = false;

    public void ready() {
        isReady = true;
    }

    public boolean isReady() {
        return isReady;
    }

    static class NoVisibilityTester {
        public static void main(String[] args) {
            NoVisibility noVisibility = new NoVisibility();
            new Thread(() -> {
                noVisibility.ready();
                System.out.println(System.nanoTime() + ": ready!");
            }).start();
            while (true) {
                if (noVisibility.isReady()) {
                    System.out.println(System.nanoTime() + ": main thread now is ready");
                    break;
                }
                System.out.println(System.nanoTime() + ": not ready");
            }
            System.out.println(System.nanoTime() + ": now exit");
        }
    }
}
