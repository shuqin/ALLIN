package zzz.study.algorithm.dynamicplan;

import zzz.study.algorithm.runtime.RuntimeMeasurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 二项式定理测试
 */
public class BinomialTest {

    public static void autoTest() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(BinomialTheorem.getInstance(i));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public static void measureTime() {
        RuntimeMeasurement rm = new RuntimeMeasurement(4);
        rm.measureTime(BinomialTheorem.class, "calcBinomialCoeffs");
        rm.showTime();
    }

    public static void handTest() {
        try {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("请输入任意数字： ");
            String param = "";
            try {
                while ((param = stdin.readLine()) != null && param.length() != 0) {
                    if (!param.matches("\\s*0|[1-9][0-9]*\\s*"))
                        throw new NumberFormatException();

                    BinomialTheorem bino = BinomialTheorem.getInstance(Integer.parseInt(param));
                    System.out.println(bino);
                }
            } finally {
                stdin.close();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("IO 出错： " + ioe.getMessage());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            System.out.println("输入有误，请按格式输入：" + nfe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCombinNum() {
        for (int k = 1; k <= 20; k++) {
            System.out.printf("C(%d,%d) = %d\n", 20, k, BinomialTheorem.combinNum(20, k));
        }

        for (int n = 10; n <= 100000000; n *= 10) {
            System.out.printf("C(%d,%d) = %d\n", n, 10, BinomialTheorem.combinNum(n, 10));
        }

        for (int k = 10; k <= 100; k += 10) {
            System.out.printf("C(%d,%d) = %d\n", k, k / 2, BinomialTheorem.combinNum(k, k / 2));
        }
    }

    public static void testValueObject() {
        BinomialTheorem bt3 = BinomialTheorem.getInstance(3);
        BinomialTheorem another3 = BinomialTheorem.getInstance(3);
        System.out.println("bt3 hashCode: " + bt3.hashCode());
        System.out.println("another3 hashCode: " + another3.hashCode());
        System.out.println("another3 == bt3 ? " + (another3 == bt3));
        System.out.println("another3.equals(bt3) ? " + another3.equals(bt3));

        Integer int100 = Integer.valueOf(100);
        Integer ano100 = Integer.valueOf(100);
        System.out.println("int100 hashCode: " + int100.hashCode());
        System.out.println("ano100 hashCode: " + ano100.hashCode());
        System.out.println("int100 == ano100 ? " + (int100 == ano100));
        System.out.println("int100.equals(ano100) ? " + int100.equals(ano100));

        String string = "am I happy ? ";
        String anoString = "am I happy ? ";
        System.out.println("string hashCode: " + string.hashCode());
        System.out.println(" anoString hashCode: " + anoString.hashCode());
        System.out.println("string == anoString ? " + (string == anoString));
        System.out.println("string.equals(anoString) ? " + string.equals(anoString));
    }

    public static void main(String[] args) {

        System.out.println("默认 JVM 最大内存： " + Runtime.getRuntime().maxMemory());

        System.out.println("********** 值对象性质检验 ************");
        testValueObject();

        System.out.println("--------- 求二项式的幂的展开式： ------------ ");

        System.out.println("********** 自动测试小实例 ************");
        autoTest();

        System.out.println("********** 手动测试实例 ************");
        handTest();

        System.out.println("******* 计算二项式系数的运行时间测试(ms) *********");
        measureTime();

        System.out.println("********** 计算组合数 ************");
        testCombinNum();

        System.out.println(" JVM 已占用总内存： " + Runtime.getRuntime().totalMemory());
        System.out.println(" JVM 可用内存： " + Runtime.getRuntime().freeMemory());

    }

}
