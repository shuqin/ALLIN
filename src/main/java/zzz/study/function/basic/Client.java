package zzz.study.function.basic;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shuqin on 17/3/31.
 */
public class Client {

    public static void main(String[] args) {
        Report.report(Arrays.asList(new String[] {"Id", "Name"}), getPersons());
        Report.report(Arrays.asList(new String[] {"Name", "Able"}), getPersons());
    }

    private static List<Person> getPersons() {
        Person s1 = new Student("s1", "liming", "Study");
        Person s2 = new Student("s2", "xueying", "Piano");
        Person t1 = new Teacher("t1", "Mr.Q", "Swim");
        Person t2 = new Teacher("t2", "Mrs.L", "Dance");
        return Arrays.asList(new Person[] {s1, s2, t1, t2});
    }

}
