package cc.lovesq.util;

public class Currency {

    public static Double fen2yuan(Long fen) {
        if (fen == null) {
            return 0.0;
        }
        return fen / 100.0;
    }
}
