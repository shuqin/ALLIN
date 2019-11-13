package zzz.study.threadprogramming.traps;

public class RelatedInconsistency {

  private int square;
  private int area;

  public RelatedInconsistency(int square) {
    this.square = square;
    computeArea();
  }

  public void set(int square) {
    this.square = square;
    computeArea();
    System.out.println("square: " + square + "  area:" + area);
  }

  private void computeArea() {
    this.area = this.square * this.square;
  }

  static class RelatedInconsistencyTest {
    public static void main(String[]args) throws InterruptedException {
      RelatedInconsistency relatedInconsistency = new RelatedInconsistency(1);
      ThreadStarter.startMultiThreads(3000, 10,
          (th) -> relatedInconsistency.set(th)
      );
    }
  }
}
