package zzz.study.function.extend;

import zzz.study.function.basic.Person;
import zzz.study.function.basic.Report;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shuqin on 17/3/31.
 */
public class Client2 {

    public static void main(String[] args) {
        Report.report(Arrays.asList(new String[] {"Id", "Name", "Able", "Salary"}), getPersons());
    }

    private static List<Person> getPersons() {
        Employee t1 = new Teacher2("t1", "Mr.Q", "Swim", "3600");
        Employee t2 = new Teacher2("t2", "Mrs.L", "Dance", "5000");

        return Arrays.asList(new Employee[] {t1, t2});
    }

}
