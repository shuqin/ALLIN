package zzz.study.function.extend;

import java.util.function.Function;

/**
 * Created by shuqin on 17/3/31.
 */
public enum EmployeeFieldConf {

    Salary("Salary", "薪水", Employee::getSalary);

    private String name;
    private String title;
    private Function<Employee, String> method;

    EmployeeFieldConf(String name, String title, Function<Employee,String> method) {
        this.name = name;
        this.title = title;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Function<Employee, String> getMethod() {
        return method;
    }
}
