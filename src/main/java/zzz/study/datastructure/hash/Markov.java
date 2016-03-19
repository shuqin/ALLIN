package zzz.study.datastructure.hash;

import java.io.*;

public class Markov {

    private static final int NWORDS = 10000;

    public static void main(String[] args) throws IOException {
        //testText();
        testFile();
    }

    public static void testText() throws IOException {
        for (int i = 0; i < 50; i++) {
            System.out.printf(" ---------- 第 %d 次执行 ----------\n", i);
            Chain chain = new Chain();
            chain.build(new ByteArrayInputStream("I have a dream , a dream that one day i could ...".getBytes()));
            try {
                chain.outputMap();
                chain.generate(NWORDS);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }

    public static void testFile() {
        try {
            Chain chain = new Chain(new FileOutputStream(
                    new File("./src/main/java/datastructure/hash/markov_output.txt")));
            chain.build(new FileInputStream(
                    new File("./src/main/java/datastructure/hash/i_have_a_dream.txt")));
            chain.outputMap();
            chain.generate(NWORDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
