package shared.util;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import shared.utils.PojoUtils;
import zzz.study.apidesign.export.common.Condition;
import zzz.study.apidesign.export.common.ExportParam;
import zzz.study.apidesign.export.common.SearchParam;
import zzz.study.patterns.composite.escondition.Match;
import zzz.study.patterns.composite.escondition.Op;
import zzz.study.patterns.composite.escondition.Range;

import java.util.Arrays;
import java.util.Objects;

public class PojoUtilsTest {

  @Test
  public void testRealize() {
    ExportParam exportParam = new ExportParam();
    exportParam.setBizType("order");
    exportParam.setCategory("baozhengjin");
    exportParam.setSource("wsc");
    exportParam.setRequestId("request"+System.currentTimeMillis());
    exportParam.setExtra(ImmutableMap.of("account", "qin"));
    exportParam.setStrategy("standard");
    SearchParam searchParam = new SearchParam();
    searchParam.setBizId(123L);
    searchParam.setStartTime(151456798L);
    searchParam.setEndTime(153456789L);
    searchParam.setConditions(Arrays.asList(
        new Condition("name", Op.eq, "sisi"),
        new Condition("pay_time", Op.range, new Range(152987654L, 153987654)),
        new Condition("state", Op.in, Arrays.asList(5,6)),
        new Condition("goods_title", Op.match, new Match("走过路过不要错过", "100%"))
    ));
    exportParam.setSearch(searchParam);

    Object paramGeneralized = PojoUtils.generalize(exportParam);

    ExportParam exportParamRestored = (ExportParam) PojoUtils.realize(paramGeneralized, ExportParam.class);
    assert Objects.equals(exportParamRestored, exportParam);

  }
}


