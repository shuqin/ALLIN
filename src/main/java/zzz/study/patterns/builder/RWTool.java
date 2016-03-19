package zzz.study.patterns.builder;

import java.io.*;

public class RWTool {

    public static BufferedReader stdin;   // 从标准输入中读取
    public static BufferedReader fileReader;  // 从指定文件中读取
    public static BufferedWriter fileWriter;  // 写入指定文件

    // 由标准输入构造的字符缓冲输入流
    // 用于从标准输入【通常是键盘】中读取数据的标准读对象
    public static BufferedReader getStdin() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    // 由指定文件构造的字符缓冲输入流
    // 用于从filename指定的文件中读取数据的文件读对象
    public static BufferedReader getFileReader(String filename)
            throws FileNotFoundException {
        return new BufferedReader(new FileReader(new File(filename).getAbsoluteFile()));
    }

    // 由指定字符构造的字符输出流
    // 用于向filename指定的文件写入数据的文件写对象
    public static BufferedWriter getFileWriter(String filename) throws IOException {
        return new BufferedWriter(new FileWriter(new File(filename).getAbsoluteFile()));
    }


}
