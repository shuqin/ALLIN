package zzz.study.foundations.basic;

/*
 * 中国象棋中，将帅不能处于同一直线上;
 * 可允许的位置集合
 */
public class ChessPosition {

    public static void chessPosition() {
        byte i = 81;
        while (i-- > 0) {
            if (i / 9 % 3 == i % 9 % 3)
                continue;
            System.out.printf("A = %d, B = %d\n", i / 9 + 1, i % 9 + 1);
        }
    }

    public static void main(String[] args) {
        chessPosition();
    }


}
