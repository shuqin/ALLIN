package cc.lovesq;

import org.apache.commons.collections.map.HashedMap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO
 * Created by qinshu on 2022/1/25
 */
public class YearMonthTest {

    public static void main(String[]args) {
        Map<Integer, Map<Integer, WeekData>> weekDataMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            String istr = "";
            if (i < 10) {
                istr += "0" + i;
            }
            else {
                istr = "" + i;
            }
            istr = "2021-"+istr;
            System.out.println(istr);
            weekDataMap.put(i, weeks(YearMonth.parse(istr)));
        }
        System.out.println(weekDataMap);
    }

    private static Map<Integer, WeekData> weeks(YearMonth yearMonth){
        LocalDate start = LocalDate.now().with(yearMonth).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = LocalDate.now().with(yearMonth).with(TemporalAdjusters.lastDayOfMonth());

        Map<Integer, WeekData> map = Stream.iterate(start, localDate -> localDate.plusDays(1l))
                .limit(ChronoUnit.DAYS.between(start, end)+1)
                .collect(Collectors.groupingBy(localDate -> localDate.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfMonth()),
                        Collectors.collectingAndThen(Collectors.toList(), WeekData::new)));
        return map;
    }
}

class WeekData {
    // 一周的开始时间
    private LocalDate start;
    // 一周的结束时间
    private LocalDate end;

    public WeekData(List<LocalDate> localDates) {
        this.start = localDates.get(0);
        this.end = localDates.get(localDates.size()-1);
    }

    @Override
    public String toString() {
        return "开始时间：" + this.start + "，结束时间：" + this.end;
    }
}