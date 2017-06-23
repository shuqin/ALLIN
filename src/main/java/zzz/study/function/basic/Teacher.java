package zzz.study.function.basic;

/**
 * Created by shuqin on 17/3/30.
 */
public class Teacher implements Person {

    private String teacherId;
    private String name;
    private String able;

    public Teacher(String teacherId, String name, String able) {
        this.teacherId = teacherId;
        this.name = name;
        this.able = able;
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
}
