package zzz.study.foundations.iolearn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NonBufferedReader {

    public static void main(String[] args) throws IOException {

        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        FileReader fReader = new FileReader(new File("./src/foundations/iolearn/bigfile.txt").getAbsoluteFile());
        while ((fReader.read()) != -1) {
        }
        end = System.currentTimeMillis();
        System.out.println("start: " + start + "\t" + "end: " + end + " ms");
        System.out.println("无缓冲读花费时间： " + (end - start) + " ms");

        start = System.currentTimeMillis();
        BufferedReader bReader = new BufferedReader(new FileReader(new File("./src/foundations/iolearn/NonBufferedReader.java").getAbsoluteFile()));
        while ((bReader.read()) != -1) {
        }
        end = System.currentTimeMillis();
        System.out.println("start: " + start + "\t" + "end: " + end + " ms");
        System.out.println("有缓冲读花费时间： " + (end - start) + " ms");

    }

}
