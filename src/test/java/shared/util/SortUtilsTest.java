package shared.util;

import avro.shaded.com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import shared.utils.SortUtils;
import zzz.study.function.basic.Person;
import zzz.study.function.basic.Student;

import java.util.Comparator;
import java.util.List;

/**
 * 排序相关的工具类
 */
public class SortUtilsTest {

    @Test
    public void testSort() {

        List<Student> students = Lists.newArrayList(
                new Student("S001", "qin", "reading"),
                new Student("S002", "ni", "reading"),
                new Student("S003", "ai", "writing"),
                new Student("S004", "li", "swing"));

        Sort nameSort = Sort.by("name");
        Comparator<Student> comparator = SortUtils.sortToComparator(nameSort, Student.class);
        students.sort(comparator);
        System.out.println(students);

    }

}
