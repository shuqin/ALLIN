package zzz.study.patterns.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Client {

    public static void main(String[] args) {

        int[] original = new int[]{0, 23, 12, 68, 45, 34, 98, 34};
        System.out.println(Arrays.toString(original));

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("根据用户的输入进行策略选择: input s[S] or f[F]");
        String choice = "";
        SortingStrategy strategy = null;
        try {
            while ((choice = in.readLine()) != null) {
                if (choice.equalsIgnoreCase("s"))
                    strategy = new StrategyForSimple();
                else if (choice.equalsIgnoreCase("f"))
                    strategy = new StrategyForFast();
                else
                    strategy = new StrategyForNull();
                int[] unsorted = Arrays.copyOf(original, original.length);
                strategy.sort(unsorted);
                System.out.println(Arrays.toString(unsorted));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
