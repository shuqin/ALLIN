package zzz.study.function;

import java.util.function.Function;

/**
 * Created by shuqin on 17/3/30.
 */
public enum FieldConf {

    Id("Id", "编号", Person::getId),
    Name("Name", "姓名", Person::getName),
    Able("Able", "能力", Person::able);

    private String name;
    private String title;
    private Function<Person, String> able;

    FieldConf(String name, String title, Function<Person,String> able) {
        this.name = name;
        this.title = title;
        this.able = able;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Function<Person, String> getAble() {
        return able;
    }
}
