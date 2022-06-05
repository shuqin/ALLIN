package cc.lovesq.study.test;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import java.util.List;

/**
 * 测试聚合分页
 * Created by qinshu on 2021/11/5
 */
public class AggregationPageable {

    @Test
    public void testCalcWorkloadCountByPage() {
        List<StatIndicatorCountVO> statIndicatorCountVOS = Lists.newArrayList(
                new StatIndicatorCountVO("", 5l), new StatIndicatorCountVO("", 8l),new StatIndicatorCountVO("", 2l)
        );

        List<StatIndicatorCountVO> pagedCounts1 = calcWorkloadCountByPage(0, 5, statIndicatorCountVOS);
        System.out.println(pagedCounts1);
        List<StatIndicatorCountVO> pagedCounts2 = calcWorkloadCountByPage(1, 5, statIndicatorCountVOS);
        System.out.println(pagedCounts2);

        List<StatIndicatorCountVO> pagedCounts3 = calcWorkloadCountByPage(2, 5, statIndicatorCountVOS);
        System.out.println(pagedCounts3);

        List<StatIndicatorCountVO> pagedCounts4 = calcWorkloadCountByPage(3, 5, statIndicatorCountVOS);
        System.out.println(pagedCounts4);


        List<StatIndicatorCountVO> pagedCounts21 = calcWorkloadCountByPage(0, 3, statIndicatorCountVOS);
        System.out.println(pagedCounts21);
        List<StatIndicatorCountVO> pagedCounts22 = calcWorkloadCountByPage(1, 3, statIndicatorCountVOS);
        System.out.println(pagedCounts22);

        List<StatIndicatorCountVO> pagedCounts23 = calcWorkloadCountByPage(2, 3, statIndicatorCountVOS);
        System.out.println(pagedCounts23);

        List<StatIndicatorCountVO> pagedCounts24 = calcWorkloadCountByPage(3, 3, statIndicatorCountVOS);
        System.out.println(pagedCounts24);

    }

    private List<StatIndicatorCountVO> calcWorkloadCountByPage(long pageNumber, long pageSize, List<StatIndicatorCountVO> workloadCounts) {
        long startCount = pageSize * pageNumber;

        // 遍历的累计值
        long countSum = 0;
        // 区间内的累计值
        long rangeSum = 0;
        //是否为区间内的第一个值
        boolean isFirst = false;
        List<StatIndicatorCountVO> pageWorkloadCountList = Lists.newArrayList();
        for (StatIndicatorCountVO workloadCount : workloadCounts) {
            String type = workloadCount.getType();
            long count = workloadCount.getCount();
            long theCount;
            countSum += count;
            if (countSum > startCount && rangeSum < pageSize) {
                if (!isFirst) {
                    isFirst = true;
                    if (pageNumber == 0 && count >= pageSize) {
                        // 第一页的数据，特殊处理
                        pageWorkloadCountList.add(StatIndicatorCountVO.builder().type(type).count(pageSize).build());
                        break;
                    } else {
                        theCount = countSum - startCount;
                    }
                } else {
                    if (count >= pageSize - rangeSum) {
                        pageWorkloadCountList.add(StatIndicatorCountVO.builder().type(type).count(pageSize - rangeSum).build());
                        break;
                    } else {
                        theCount = count;
                    }
                }
                pageWorkloadCountList.add(StatIndicatorCountVO.builder().type(type).count(theCount).build());
                rangeSum += theCount;
            }
        }

        return pageWorkloadCountList;
    }



}

@ToString
@Getter
@Builder
class StatIndicatorCountVO {

    private String type;

    private Long count;
}


