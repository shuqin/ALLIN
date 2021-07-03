package zzz.study.algorithm.permutation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class PermutationTest {

    /*
     * 测试全排列
     * @num  给定的正整数
     */
    public static void testPerm(int num) {
        try {
            System.out.println("n = " + num);
            ArrayList<LinkedList<Integer>> perms = new Permutation().getPerm(num);
            System.out.println("Permutation: ");
            System.out.println(perms);
        } catch (NumberFormatException nfe) {
            System.out.println("输入有误，只能输入数字：" + nfe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println("参数出错，必须是正整数：" + iae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testPowerSet(int num) {
        try {
            System.out.println("n = " + num);
            ArrayList<LinkedList<Integer>> powerset = new PowerSet().getPowerset(num);
            System.out.println("Powerset: ");
            System.out.println(powerset);
        } catch (NumberFormatException nfe) {
            System.out.println("输入有误，只能输入数字：" + nfe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println("参数出错，必须是正整数：" + iae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        int n = 0;

        try {
            String param = stdin.readLine();
            n = Integer.parseInt(param);

            System.out.println("n = " + n);

            ArrayList<LinkedList<Integer>> perms = new Permutation().getPerm(n);
            System.out.println("Permutation: ");
            System.out.println(perms);

            ArrayList<LinkedList<Integer>> powerset = new PowerSet().getPowerset(n);
            System.out.println("Powerset: ");
            System.out.println(powerset);

        } catch (IOException ioe) {
            System.out.println("IO 出错： " + ioe.getMessage());
        } catch (NumberFormatException nfe) {
            System.out.println("输入有误，只能输入数字：" + nfe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println("参数出错，必须是正整数：" + iae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
