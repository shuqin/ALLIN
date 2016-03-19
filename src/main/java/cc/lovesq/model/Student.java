package cc.lovesq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by shuqin on 16/10/24.
 */
@ToString
@Getter
@Setter
public class Student {

    private String name;
    private int age;
    private Address address;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
