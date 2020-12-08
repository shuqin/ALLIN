package zzz.study.algorithm.dividing;


import java.util.function.Function;

/**
 * 假设有长度为 N 的每份净值函数离散波动曲线 f(n)，进行了 m 次定投。那么，定投获得的利率是怎样的?
 *
 * 假设 f(n) 是一个余弦曲线：f(n) = cos(n) + BASE，BASE 是个常量，不妨设 BASE = 2；
 *
 * 在 0, N/m, 2N/m, ..., (m-1)N/m 处投入，本金为 P , 则每次投入 P/m, 每次的份数为
 * (P/m) / (cos(0)+2), (P/m) / (cos(N/m)+2), ..., (P/m) / (cos(((m-1)N/m))+2)。
 *
 * 在 t 点赎出,
 * 最终净值额 = (P/m) * { 1/(cos(0)+2) + 1/(cos(N/m)+2) + ... + 1 / (cos((m-1)N/m)+2) } * (cos(t)+2)
 *
 * (1/m) * { 1/(cos(0)+2) + 1/(cos(N/m)+2) + ... + 1 / (cos((m-1)N/m)+2) } * (cos(t)+2) - 1
 * 盈利率为 (cos(t)+2) * (1/m) * ( 1/(cos(0)+2) + 1/(cos(N/m)+2) + ... + 1 / (cos((m-1)N/m)+2) ) - 1。
 *
 * 盈利率与本金无关，与定投踩点有关.
 */

public class AutomaticInvestmentPlan {

    public static void main(String[] args) {

        aip(8*Math.PI, 12, 8*Math.PI, (x) -> fcos(x,2));
        aip(8*Math.PI, 52, 8*Math.PI, (x) -> fcos(x,2));
        aip(9*Math.PI, 12, 9*Math.PI, (x) -> fcos(x,2));
        aip(9*Math.PI, 52, 9*Math.PI, (x) -> fcos(x,2));
        aip(12*Math.PI, 12, 12*Math.PI, (x) -> fcos(x,2));
        aip(12*Math.PI, 52, 12*Math.PI, (x) -> fcos(x,2));
        aip(24*Math.PI, 12, 24*Math.PI, (x) -> fcos(x,2));

        aip(24*Math.PI, 52, 24*Math.PI, (x) -> fcos(x,2));
        aip(48*Math.PI, 52, 48*Math.PI, (x) -> fcos(x,2));
        aip(72*Math.PI, 52, 72*Math.PI, (x) -> fcos(x,2));
        aip(96*Math.PI, 52, 96*Math.PI, (x) -> fcos(x,2));
    }

    public static void aip(double N, int m, double t, Function<Double,Double> netValueFunc) {
        double totalFens = 0.0;
        for (int i=0; i < m; i++) {
            double fens = 1 / netValueFunc.apply(i*N/(double)m);
            totalFens += fens;
        }
        double finalFens = netValueFunc.apply(t) * totalFens / m;
        String result = String.format("N=%f, m=%d, t=%f, totalFens=%f, finalFens=%f, profit=%f", N, m, t, totalFens, finalFens, finalFens-1);
        System.out.println(result);
    }

    public static double fcos(double x, double base) {
        return Math.cos(x) + base;
    }

}
