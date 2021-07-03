package zzz.study.foundations.iolearn;

import shared.utils.DateUtil;

import java.io.*;
import java.util.Date;

public class DirTool {

    private DirTool() {
    }

    /**
     * printDirInfo: 根据给定路径名列出该路径下的所有文件和目录(不包括子目录)
     * path: 给定［绝对和相对］路径的字符串表示
     */

    public static void printDirInfo(String path) {

        File filePath = new File(path).getAbsoluteFile();
        if (filePath.isFile()) {
            System.out.println("给定的是一个文件： " + filePath.getName());
            return;
        }
        File[] files = filePath.listFiles();
        System.out.println("给定目录路径： " + filePath.getAbsolutePath());
        for (File file : files) {
            if (file.isFile()) {
                System.out.println("[F]" + file.getName() + " ");
            } else if (file.isDirectory()) {
                System.out.println("[D]" + file.getName() + " ");
            }
        }
    }

    /**
     * printDirTree:
     * 根据给定路径名列出该路径下的所有文件和目录(包括子目录及其文件)，
     * 并以树形显示出来
     *
     * @throws FileNotFoundException
     */
    public static void printDirTree(String path) throws FileNotFoundException {
        File filePath = new File(path);
        int depth = 0;
        PrintStream tmPrintStream = System.out;
        PrintStream output =
                new PrintStream(new BufferedOutputStream(
                        new FileOutputStream("./src/foundations/iolearn/目录树.txt")));
        System.setOut(output);
        System.out.println("************** 打印该路径的目录树 *************");
        printDirTree(filePath.getAbsoluteFile(), depth);
        output.flush();
        System.setOut(tmPrintStream);
    }


    private static void printDirTree(File filePath, int depth) {

        printTab(depth);

        if (filePath.isFile()) {
            System.out.println("[F]" + filePath.getName());
            return;
        }
        System.out.println("[D]" + filePath);
        File[] subpaths = filePath.listFiles();
        for (File path : subpaths) {
            printDirTree(path, depth + 1);
        }
    }


    /**
     * printPathinfo: 根据给定路径名打印相关信息
     */

    public static void printPathinfo(String path) {

        divideLine();

        try {
            File filePath = new File(path);
            // getPath： 将抽象路径名转化为相应的字符串形式的路径名
            System.out.println("给定路径：" + filePath.getPath());
            // getPath： 将抽象路径名转化为相应的规范路径名字符串【绝对路径名】
            System.out.println("是否绝对路径名 ? " + filePath.isAbsolute());
            System.out.println("绝对路径名：" + filePath.getCanonicalPath());
            // getParent: 获取该抽象路径名的父目录路径名字符串
            System.out.println("父目录路径名：" + new File(filePath.getCanonicalPath()).getParent());
            System.out.println("是否文件?  " + filePath.isFile());
            System.out.println("是否目录?  " + filePath.isDirectory());
            System.out.println("是否可读?  " + filePath.canRead());
            System.out.println("是否可写?  " + filePath.canWrite());
            System.out.println("最后一次被修改的时间: " + DateUtil.format(new Date(filePath.lastModified())));
            System.out.println("该目录下所有文件和目录：");
            DirTool.printDirInfo(path);


        } catch (IOException e) {
            e.printStackTrace(System.out);
        } catch (SecurityException e) {
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    private static void divideLine() {

        System.out.println("----------------------------");
    }

    private static void printTab(int n) {
        while (n > 0) {
            System.out.printf("  ");
            n--;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        String relPath = ".";
        DirTool.printPathinfo(relPath);

        String absPath = "/home/shuqin1984/pv/";
        DirTool.printPathinfo(absPath);
        DirTool.printDirTree(absPath);

        String filePath = "/home/shuqin1984/pv/做一个行万里路的旅者.odt";
        DirTool.printPathinfo(filePath);
        // DirTool.printDirTree(filePath);


    }


}
