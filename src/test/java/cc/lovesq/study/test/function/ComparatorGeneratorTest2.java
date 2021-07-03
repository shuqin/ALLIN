package cc.lovesq.study.test.function;


import cc.lovesq.model.Student;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import zzz.study.function.comparator.ComparatorGenerator2;
import zzz.study.function.comparator.ConnBriefInfo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Date 2021/6/11 6:18 下午
 * @Created by qinshu
 */
public class ComparatorGeneratorTest2 {

    @Test
    public void testGetComparator() {
        List<ConnBriefInfo> connBriefInfoList = Arrays.asList(
                new ConnBriefInfo(3L, "2021-06-11", "2021-05-12"),
                new ConnBriefInfo(5L, "2021-06-19", "2021-05-19"),
                new ConnBriefInfo(2L, "2021-06-15", "2021-05-15")
        );


        List<ConnBriefInfo> sortedByFirstTime = connBriefInfoList.stream()
                .sorted(ComparatorGenerator2.getComparator(ConnBriefInfo::getFirstTime, true))
                .collect(Collectors.toList());

        System.out.println(sortedByFirstTime);

        List<ConnBriefInfo> sortedByVisitorCount = connBriefInfoList.stream()
                .sorted(ComparatorGenerator2.getComparator(cb -> String.valueOf(cb.getVisitCount()), false))
                .collect(Collectors.toList());

        System.out.println(sortedByVisitorCount);

        List<ConnBriefInfo> sortedByVisitorCountDesc = connBriefInfoList.stream()
                .sorted(ComparatorGenerator2.getComparator(new Sort(Sort.Direction.DESC, "visitCount"), ConnBriefInfo.class))
                .collect(Collectors.toList());

        System.out.println(sortedByVisitorCountDesc);


        List<ConnBriefInfo> sortedByLastTime = connBriefInfoList.stream()
                .sorted(ComparatorGenerator2.getComparator(new Sort(Sort.Direction.DESC, "lastTime"), ConnBriefInfo.class))
                .collect(Collectors.toList());

        System.out.println(sortedByLastTime);

        List<Student> students = Arrays.asList(
                new Student("qin", 32), new Student("ni", 26),
                new Student("lian", 27)
        );

        List<Student> stuOrderByAge = students.stream().sorted(ComparatorGenerator2.getComparator(new Sort(Sort.Direction.DESC, "age"), Student.class))
                .collect(Collectors.toList());

        System.out.println(stuOrderByAge);

    }
}
