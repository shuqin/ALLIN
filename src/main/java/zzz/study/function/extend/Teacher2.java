package zzz.study.function.extend;

/**
 * Created by shuqin on 17/3/30.
 */
public class Teacher2 implements Employee {

    private String teacherId;
    private String name;
    private String able;

    private String salary;

    public Teacher2(String teacherId, String name, String able, String salary) {
        this.teacherId = teacherId;
        this.name = name;
        this.able = able;
        this.salary = salary;
    }

    @Override
    public String getId() {
        return teacherId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String able() {
        return able;
    }

    public String getSalary() {
        return salary;
    }
}
