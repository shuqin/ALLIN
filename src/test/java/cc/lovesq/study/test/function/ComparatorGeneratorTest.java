package cc.lovesq.study.test.function;

import org.junit.Test;
import org.springframework.data.domain.Sort;
import zzz.study.function.comparator.ComparatorGenerator;
import zzz.study.function.comparator.ConnBriefInfo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Date 2021/6/11 6:18 下午
 * @Created by qinshu
 */
public class ComparatorGeneratorTest {

    @Test
    public void testGetComparator() {
        List<ConnBriefInfo> connBriefInfoList = Arrays.asList(
                new ConnBriefInfo(3L, "2021-06-11", "2021-05-12"),
                new ConnBriefInfo(5L, "2021-06-19", "2021-05-19"),
                new ConnBriefInfo(2L, "2021-06-15", "2021-05-15")
        );


        List<ConnBriefInfo> sortedByFirstTime = connBriefInfoList.stream()
                .sorted((Comparator<? super ConnBriefInfo>) ComparatorGenerator.getComparator(null, "firstTime"))
                .collect(Collectors.toList());

        System.out.println(sortedByFirstTime);

        List<ConnBriefInfo> sortedByVisitorCount = connBriefInfoList.stream()
                .sorted((Comparator<? super ConnBriefInfo>) ComparatorGenerator.getComparator(new Sort("visitCount")))
                .collect(Collectors.toList());

        System.out.println(sortedByVisitorCount);

        List<ConnBriefInfo> sortedByVisitorCountDesc = connBriefInfoList.stream()
                .sorted((Comparator<? super ConnBriefInfo>) ComparatorGenerator.getComparator(new Sort(Sort.Direction.DESC, "visitCount")))
                .collect(Collectors.toList());

        System.out.println(sortedByVisitorCountDesc);


        List<ConnBriefInfo> sortedByLastTime = connBriefInfoList.stream()
                .sorted((Comparator<? super ConnBriefInfo>) ComparatorGenerator.getComparator(new Sort(Sort.Direction.DESC, "lastTime")))
                .collect(Collectors.toList());

        System.out.println(sortedByLastTime);

    }
}
