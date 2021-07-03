package zzz.study.patterns.templateMethod;

import java.util.Arrays;

public class ShowComparator {

    public static void main(String[] args) {

        Student s1 = new Student(0001, "Zhangxiaomin", "女", 94.5);
        Student s2 = new Student(0002, "Lilan", "女", 90.5);
        Student s3 = new Student(0003, "Xiaofeng", "男", 87.5);
        Student s4 = new Student(0004, "Yangmin", "男", 90.5);

        Student[] students = new Student[]{s1, s2, s3, s4};

        System.out.println("根据姓名排序：");
        Arrays.sort(students, new NameComparator());
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }

        System.out.println("根据成绩排序：");
        Arrays.sort(students, new GradeComparator());
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
    }

}
