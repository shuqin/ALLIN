/**
 * 问题描述： 给定 n 个布尔变量，打印所有真值组合。
 * 例如， n = 2 时 ， 所有真值组合为 (true, false),(true, true),(false, true),(false, false).
 * <p>
 * 算法的基本思路：
 * 使用一个长度为 n 的数组存储着 n 个布尔变量；位 1 表示 true , 位 0 表示 false,
 * 生成每一个真值元组，实际上是生成一个由 0 和 1 表示的 数组。
 * <p>
 * 生成每一个真值元组的方法：从零开始，逐次加一。
 * 比如  000 -> 001 -> 010 -> 011 -> 100 -> 101 -> 110 -> 111
 * <p>
 * 具体算法：
 * 每次都从最低位开始，将最低位作【置 一】处理 ：
 * ①  如果最低位是 0 ， 则置 1 即可【不进位】；
 * ②  如果最低位为 1 ， 则置 0 ； 由于有进位， 比如进一步将次低位作【置一】处理。
 * 直至某一位由 0 置 1 为止 【不进位】。
 * <p>
 * 例如： 011 :
 * ①  最低位为1， 置 0 ， 并进位；
 * ②  次低位为1， 置 0 ， 并进位；
 * ③  次次低位为 0 ， 置 1。 结果为 100
 * <p>
 * <p>
 * 技巧：
 * ① 由于这里只涉及置 1 或 置 0 ， 实际上就是置 true 或 置 false ，
 * 因此， 可以直接在数组里存储布尔值，并不必要在 1,0 和 true, false 之间转换。
 * <p>
 * ②  设置一个结束标识变量 endflag ，当 1..1 -> 0..0 时 设置为 true
 * <p>
 * [Check OK]
 */

package zzz.study.algorithm.permutation;

import zzz.study.utils.RegexUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Combination {

    private boolean[] combination;
    private long count;
    private boolean endflag;

    public Combination(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("参数必须为正整数");
        if (combination == null) {
            combination = new boolean[n];
            count = 0;
            endflag = false;
        }
    }

    public static void main(String[] args) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        try {
            String inputStr = "";
            int num = 0;
            for (; ; ) {
                System.out.println("请输入一个正整数: ");
                inputStr = stdin.readLine();
                if (RegexUtil.isPositiveNumPattern(inputStr)) {
                    num = Integer.parseInt(inputStr.trim());
                    if (num == 0) break;
                    System.out.println("n = " + num);
                    Combination c = new Combination(num);
                    c.solution();
                } else {
                    System.out.println("您输入的不是正整数，请重新输入！ 退出请输入 0");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 求解问题，打印所有的真值组合结果。
     *
     */
    public void solution() {
        System.out.println("n = " + combination.length + " ***** 所有真值组合： ");
        do {
            System.out.println(getOneTuple());
            count++;
            increOne();
        } while (!terminate());
        System.out.println("真值组合数： " + count);
    }

    /**
     * 逐次加一，生成每一个真值元组
     *
     */
    private void increOne() {
        int i;
        for (i = 0; i < combination.length; i++) {
            // 若为 0 ，则置 1 ， 结束。
            if (combination[i] == false) {
                combination[i] = true;
                break;
            } else {
                // 若为 1， 则置 0， 并通过 i++ 转至次低位进行相同处理
                combination[i] = false;
            }
        }
        // 由 1..1 -> 0..0 时, 设置 endflag = true;
        if (i == combination.length) {
            endflag = true;
        }
    }

    /**
     *  根据整数数组表示生成的真值元组，转化为布尔数组表示生成的真值元组。
     *
     */
    private String getOneTuple() {
        StringBuilder tuple = new StringBuilder("(");
        for (int i = 0; i < combination.length; i++) {
            tuple.append(combination[i]);
            tuple.append(",");
        }
        // 删除 多余的 逗号
        tuple.deleteCharAt(tuple.length() - 1);
        tuple.append(")");
        return tuple.toString();
    }

    /**
     * 终止条件： 结束标识符 endflag = true;
     *
     */
    private boolean terminate() {
        return endflag == true;
    }


}
