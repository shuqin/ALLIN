package zzz.study.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import zzz.study.function.basic.Person;
import zzz.study.function.basic.Student;
import zzz.study.function.basic.Teacher;

/**
 * Created by shuqin on 17/11/23.
 */
public class StudentOutput {

  static List<String> fields = Arrays.asList("studentId", "studentName", "studentAble");

  public static void main(String[] args) {
    List<Person> students = getPersons();
    List<String> stundentInfos = students.stream().map(
        p -> getOneStudentInfo(p, fields)
    ).collect(
        Collectors.toList());
    System.out.println(String.join("\n", stundentInfos));
  }

  private static String getOneStudentInfo(Person p, List<String> fields) {
    List<String> stuInfos = new ArrayList<>();
    fields.forEach(
        field -> {
          ReportFieldConfig fieldConfig = FieldsConfigLoader.getFieldConfig(field);
          Binding binding = new Binding();
          binding.setVariable("stu", p);
          GroovyShell shell = new GroovyShell(binding);
          Object result = shell.evaluate(fieldConfig.getScript());
          //System.out.println("result from groovy script: " + result);
          stuInfos.add(String.valueOf(result));
        }
    );
    return String.join(",", stuInfos);
  }

  private static List<Person> getPersons() {
    Person s1 = new Student("s1", "liming", "Study");
    Person s2 = new Student("s2", "xueying", "Piano");
    return Arrays.asList(new Person[]{s1, s2});
  }

}
