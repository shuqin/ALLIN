package zzz.study.function;

import com.google.common.collect.Lists;
import zzz.study.function.basic.Student;
import zzz.study.function.refactor.StreamUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qin.shu
 * @Date 2022/8/27
 * @Description
 */
public class StreamUtilTest {

    public static void main(String[] args) {
        List<Student> students = Lists.newArrayList(
                new Student("S001", "qin", "reading"),
                new Student("S002", "ni", "reading"),
                new Student("S003", "ai", "writing"),
                new Student("S004", "li", "swing")
        );
        System.out.println(StreamUtil.map(students, Student::getName));
        System.out.println(StreamUtil.mapToSet(students, Student::able));
        System.out.println(StreamUtil.filter(students, s -> s.able().equals("reading")));
        System.out.println(StreamUtil.filterCount(students, s -> s.able().equals("reading")));
        System.out.println(StreamUtil.group(students, Student::able));
        System.out.println(StreamUtil.toMap(students, Student::able));
        System.out.println(StreamUtil.toMap(students, Student::getId, Student::able));
        System.out.println(students.stream().filter(s -> s.getName().contains("i")).map(Student::able).collect(Collectors.joining()));
    }
}
