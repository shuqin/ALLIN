package zzz.study.foundations.iolearn;

import java.io.File;
import java.io.IOException;

public class DirectoryCreating {

    public static void main(String[] args) throws IOException {
        createDirectory("/home/ss/ff/");
        createDirectory("/home/我的家/高中/高二/");
    }

    public static void createDirectory(String path) throws IOException {

        System.out.println("要创建的目录是： " + path);
        String root = path.substring(0, path.lastIndexOf("/") + 1);
        if (!root.equalsIgnoreCase("/")) {
            int start = 0;
            int end = 0;
            if (path.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = path.indexOf("/", start);
            while (end > start) {
                System.out.println("start = " + start + "\tend = " + end);
                String subDirectory = new String(path.substring(0, end + 1).getBytes("GBK"), "iso-8859-1");
                File file = new File(subDirectory);
                if (!file.exists()) {
                    System.out.println("要创建的子目录： " + new String(subDirectory.getBytes("iso-8859-1"), "GBK"));
                    if (file.mkdir()) {
                        System.out.printf("创建目录成功!\t");
                        System.out.println("---subDirectory: " + new String(subDirectory.getBytes("iso-8859-1"), "GBK"));
                    } else {
                        System.out.println("创建目录失败！");
                    }
                }
                start = end + 1;
                end = path.indexOf("/", start);
            }
        }
    }

}
