package zzz.study.patterns.mediator;

public class Test {

    public static void main(String[] args) {

        Mediator m = new Mediator();

        Student s1 = new Student("S1001", m);
        Student s2 = new Student("S1002", m);

        Department d1 = new Department("DN305", m);
        Department d2 = new Department("DS903", m);

        s1.setDepartment(d1);
        s2.setDepartment(d2);

        System.out.println("d1 --- s1: " + d1.getStudents().contains(s1));
        System.out.println("d1 --- s2: " + d1.getStudents().contains(s2));
        System.out.println("d2 --- s1: " + d2.getStudents().contains(s1));
        System.out.println("d2 --- s2: " + d2.getStudents().contains(s2));
        System.out.println(m);

        s1.setDepartment(d2);
        System.out.println("d1 --- s1: " + d1.getStudents().contains(s1));
        System.out.println("d1 --- s2: " + d1.getStudents().contains(s2));
        System.out.println("d2 --- s1: " + d2.getStudents().contains(s1));
        System.out.println("d2 --- s2: " + d2.getStudents().contains(s2));
        System.out.println(m);

        s2.setDepartment(d1);
        System.out.println("d1 --- s1: " + d1.getStudents().contains(s1));
        System.out.println("d1 --- s2: " + d1.getStudents().contains(s2));
        System.out.println("d2 --- s1: " + d2.getStudents().contains(s1));
        System.out.println("d2 --- s2: " + d2.getStudents().contains(s2));
        System.out.println(m);

        d1.addStudent(s1);
        System.out.println("d1 --- s1: " + d1.getStudents().contains(s1));
        System.out.println("d1 --- s2: " + d1.getStudents().contains(s2));
        System.out.println("d2 --- s1: " + d2.getStudents().contains(s1));
        System.out.println("d2 --- s2: " + d2.getStudents().contains(s2));
        System.out.println(m);
    }

}
