package zzz.explore.ruleengine.sample.model;

import java.math.BigDecimal;

interface IterStrategy {
    void iterCalcOne(PI pi);
}

/**
 * @author qin.shuq
 * @see http://zh.wikipedia.org/wiki/%E5%9C%93%E5%91%A8%E7%8E%87
 */
public class PI {

    private static final IterStrategy leibnizStrategy = new LeibnizIterStrategy();
    private static final IterStrategy wallisStrategy = new WallisIterStrategy();
    private static final IterStrategy arctanStrategy = new ArctanIterStrategy();
    private static final IterStrategy ballardStrategy = new BallardIterStrategy();
    private BigDecimal pi;    // PI 值
    private int iterValue;   // 迭代值
    private IterStrategy iterStrategy;   // 迭代策略
    public PI() {
        iterStrategy = IterStrategySetting.WallisMethod.getStrategy();
        this.pi = BigDecimal.valueOf(IterStrategySetting.WallisMethod.getInitPi());
        this.iterValue = IterStrategySetting.WallisMethod.getInitIterValue();
    }
    public PI(IterStrategy strategy) {
        this.iterStrategy = strategy;
        this.pi = BigDecimal.valueOf(IterStrategySetting.toEnum(this.iterStrategy).getInitPi());
        this.iterValue = IterStrategySetting.toEnum(this.iterStrategy).getInitIterValue();
    }

    /**
     * 迭代一次的计算
     */
    public void iterOne() {
        iterStrategy.iterCalcOne(this);
    }

    public BigDecimal getPi() {
        return pi;
    }

    public void setPi(BigDecimal pi) {
        this.pi = pi;
    }

    public int getIterValue() {
        return iterValue;
    }

    public void setIterValue(int iterValue) {
        this.iterValue = iterValue;
    }

    public void setIterStrategy(IterStrategy iterStrategy) {
        this.iterStrategy = iterStrategy;
        this.pi = BigDecimal.valueOf(IterStrategySetting.toEnum(this.iterStrategy).getInitPi());
        this.iterValue = IterStrategySetting.toEnum(this.iterStrategy).getInitIterValue();
    }

    public enum IterStrategySetting {
        LeibnizMethod(leibnizStrategy, 4, 1),   //  PI: 3.141590653589692    iterValue: 1000000
        WallisMethod(wallisStrategy, 2, 1),     //  PI: 3.141591082795387    iterValue: 1000000

        // PI: 3.141592653589793057092897568389359
        //       021087492184878678890301622544682
        //       870922262624376585084262078791652
        //       664665170934387960240111985365743
        //       816329749380749612568341860137676
        //       992899578827196104523046458933737
        //       232212198503616995641765457187290
        //       803101355648406923221343642309071
        //       354773081438421905649763339659738
        //       095339778728962929492812141689849872323987745
        // iterValue: 183
        ArctanMethod(arctanStrategy, 4 * ((double) 44 / 57 + (double) 7 / 239 - (double) 12 / 682 + (double) 24 / 12943), 1),

        ballardMethod(ballardStrategy, 0.015625 * (-32 - (double) 1 / 3 + 256 - (double) 64 / 3 - 0.8 - (double) 4 / 7 + (double) 1 / 9), 0);

        private IterStrategy strategy;
        private double initPi;
        private int initIterValue;

        private IterStrategySetting(IterStrategy strategy, double initPi, int initIterValue) {
            this.strategy = strategy;
            this.initPi = initPi;
            this.initIterValue = initIterValue;
        }

        public static IterStrategySetting toEnum(IterStrategy strategy) {
            for (IterStrategySetting s : IterStrategySetting.values()) {
                if (strategy.equals(s.getStrategy())) {
                    return s;
                }
            }
            return null;
        }

        public double getInitPi() {
            return initPi;
        }

        public int getInitIterValue() {
            return initIterValue;
        }

        public IterStrategy getStrategy() {
            return strategy;
        }

        public void setStrategy(IterStrategy strategy) {
            this.strategy = strategy;
        }

    }

}

class LeibnizIterStrategy implements IterStrategy {

    public void iterCalcOne(PI pi) {
        /**
         * according to formula :
         * PI/4 = (1-1/3+1/5-1/7+1/9-...)
         */
        int iterValue = pi.getIterValue() + 2;
        pi.setIterValue(iterValue);
        int sign = (iterValue % 4 == 3) ? -1 : 1;
        pi.setPi(BigDecimal.valueOf(pi.getPi().doubleValue() + (double) 4 * sign / iterValue));
    }

}

class WallisIterStrategy implements IterStrategy {

    public void iterCalcOne(PI pi) {
        /**
         * according to formula :
         * PI/2 = (2/1 * 2/3) * (4/3 * 4/5) * (6/5 * 6/7) ...
         */
        long iterValue = pi.getIterValue();
        double newpi = pi.getPi().doubleValue() * (1 + (double) 1 / (iterValue * (iterValue + 2)));
        pi.setPi(BigDecimal.valueOf(newpi));
        pi.setIterValue((int) (iterValue + 2));
    }

}

/**
 * PI: 3.141592653589793    iterValue: 183
 */
class ArctanIterStrategy implements IterStrategy {

    public void iterCalcOne(PI pi) {
        /**
         * according to formula :
         *  PI/4 = 44arctan(1/57) + 7arctan(1/239) - 12arctan(1/682) + 24arctan(1/12943)
         *  arctan(x) = x - 1/3*x^3 + 1/5*x^5 - 1/7*x^7
         */
        long iterValue = pi.getIterValue() + 2;
        int sign = (iterValue % 4 == 3) ? -1 : 1;
        BigDecimal part1 = BigDecimal.valueOf((double) 44 * Math.pow((double) 1 / 57, iterValue));
        BigDecimal part2 = BigDecimal.valueOf((double) 7 * Math.pow((double) 1 / 239, iterValue));
        BigDecimal part3 = BigDecimal.valueOf((double) 12 * Math.pow((double) 1 / 682, iterValue));
        BigDecimal part4 = BigDecimal.valueOf((double) 24 * Math.pow((double) 1 / 12943, iterValue));
        BigDecimal incre = BigDecimal.valueOf(4 * sign * ((double) 1 / iterValue)).multiply(
                part1.add(part2).subtract(part3).add(part4));
        pi.setPi(pi.getPi().add(incre));
        pi.setIterValue((int) iterValue);
    }

}

class BallardIterStrategy implements IterStrategy {

    /**
     * PI = 0.015625* SUM(0-n) {(-1)^n/2^(10*n)*(-32/(4n+1)-1/(4n+3)+256/(10n+1)-64/(10n+3)-4/(10n+5)-4/(10n+7)+1/(10n+9)))}
     */
    public void iterCalcOne(PI pi) {
        int iterValue = pi.getIterValue() + 1;
        int sign = (iterValue % 2 == 0) ? 1 : -1;
        BigDecimal part1 = BigDecimal.valueOf(-32 / (double) (4 * iterValue + 1));
        BigDecimal part2 = BigDecimal.valueOf(-1 / (double) (4 * iterValue + 3));
        BigDecimal part3 = BigDecimal.valueOf(256 / (double) (10 * iterValue + 1));
        BigDecimal part4 = BigDecimal.valueOf(-64 / (double) (10 * iterValue + 3));
        BigDecimal part5 = BigDecimal.valueOf(-4 / (double) (10 * iterValue + 5));
        BigDecimal part6 = BigDecimal.valueOf(-4 / (double) (10 * iterValue + 7));
        BigDecimal part7 = BigDecimal.valueOf(1 / (double) (10 * iterValue + 9));
        BigDecimal incre = BigDecimal.valueOf(0.015625 * sign / Math.pow(2, 10 * iterValue)).multiply(
                part1.add(part2).add(part3).add(part4).add(part5).add(part6).add(part7));
        pi.setPi(pi.getPi().add(incre));
        System.out.println(iterValue);
        pi.setIterValue((int) iterValue);
    }

}

