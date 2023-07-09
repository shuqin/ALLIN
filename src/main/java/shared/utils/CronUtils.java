package shared.utils;

import com.cronutils.mapper.CronMapper;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.CronFieldName;
import com.cronutils.parser.CronParser;
import org.apache.commons.lang3.StringUtils;
import shared.constants.CommonValues;

/**
 * 定时任务工具类
 * Created by wenjin.xu on 2022/9/21
 */
public class CronUtils {

    private static final String EXPRESSION_FORMAT = "TZ=Asia/Shanghai %s %s %s %s %s %s";

    /**
     * 将unix定时任务表达式转为CronJob定时表达式（规则可参考：https://www.cnblogs.com/lazyInsects/p/8075487.html）
     */
    public static String convertCronJobExpression(String unixCronExpression) {
        if (StringUtils.isEmpty(unixCronExpression)) {
            return null;
        }
        Cron cron = convertUnixCronToQuartzCron(unixCronExpression);

        return String.format(EXPRESSION_FORMAT,
                cron.retrieve(CronFieldName.SECOND).getExpression().asString(),
                cron.retrieve(CronFieldName.MINUTE).getExpression().asString(),
                cron.retrieve(CronFieldName.HOUR).getExpression().asString(),
                cron.retrieve(CronFieldName.DAY_OF_MONTH).getExpression().asString(),
                cron.retrieve(CronFieldName.MONTH).getExpression().asString(),
                calcDayOfWeek(unixCronExpression)
        );
    }

    /**
     * 将unix定时任务表达式转为Cron对象
     */
    private static Cron convertUnixCronToQuartzCron(String unixCronExpression) {
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX);
        CronParser parser = new CronParser(cronDefinition);
        Cron unixCron = parser.parse(unixCronExpression);

        CronMapper cronMapper = CronMapper.fromUnixToQuartz();
        return cronMapper.map(unixCron);
    }

    /**
     * 计算dayOfWeek表达式
     * NOTE：0-6：星期天-星期六
     */
    private static String calcDayOfWeek(String unixCronExpression) {
        // 解析出最后一个字符串
        return StringUtils.substringAfterLast(unixCronExpression, CommonValues.SPACE);
    }

}
