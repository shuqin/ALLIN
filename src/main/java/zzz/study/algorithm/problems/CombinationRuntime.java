package zzz.study.algorithm.problems;

import zzz.study.algorithm.runtime.CommandRuntime;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class CombinationRuntime {

    public static void main(String[] args) {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("runtime.txt"));
            fileWriter.write("runtime: ");
            fileWriter.newLine();
            for (int i = 1; i < 30; i++) {
                CommandRuntime comRuntime = new CommandRuntime(new CombinationCommand(i));
                long runtime = comRuntime.runtime();
                fileWriter.write("n = " + i + " : " + runtime + " ms");
                fileWriter.newLine();
            }
            System.out.println("over.");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
