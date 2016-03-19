package zzz.study.patterns.templateMethod;

public class Student {

    private Integer id;
    private String name;
    private String sex;
    private double grade;

    public Student(Integer id, String name, String sex, double grade) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String toString() {
        return id + " " + name + " " + sex + " " + grade;
    }

}
