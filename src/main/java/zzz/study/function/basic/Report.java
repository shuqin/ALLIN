package zzz.study.function.basic;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shuqin on 17/3/30.
 */
public class Report {

    public static void report(List<String> fields, List<Person> persons) {
        String reportTitles = fields.stream().map(
                field -> FieldConfAccompany.getInstance(field).getTitle()
        ).collect(Collectors.joining(","));

        List<String> rows = persons.stream().map(
                p -> fields.stream().map(
                        field -> FieldConfAccompany.getInstance(field).getMethod().apply(p)
                ).collect(Collectors.joining(","))
        ).collect(Collectors.toList());

        System.out.println(reportTitles);
        System.out.println(String.join("\n",rows));

    }

}
