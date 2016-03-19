package zzz.study.codesmell;

import com.google.common.collect.Lists;
import org.apache.calcite.util.Pair;
import shared.utils.JsonUtil;
import zzz.study.patterns.collector.OrderInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author qin.shu
 * @Date 2022/8/7
 * @Description
 */
public class NestedLambda {

    private static final String EDU_STUDENT_INFO = "student_info";
    private static final String STUDENT_ID = "student_id";
    private static final String VERSION = "version";
    private static final String ID_CARD_NO = "id_card";

    public void nestedLambda() {
        List<OrderInfo> orderInfoList = Lists.newArrayList();
        Map<Pair, List<String>> sameVersionOrderMap = new HashMap();
        orderInfoList.forEach(orderInfo -> buildStudentPair(orderInfo).ifPresent(
                idVersionPair -> setOrderMap(idVersionPair, orderInfo, sameVersionOrderMap)));

    }

    private void setOrderMap(Pair idVersionPair, OrderInfo orderInfo, Map<Pair, List<String>> sameVersionOrderMap) {
        sameVersionOrderMap
                .computeIfAbsent(idVersionPair, k -> Lists.newArrayList())
                .add(orderInfo.getOrderNo());
    }

    private Optional<Pair> buildStudentPair(OrderInfo orderInfo) {
        return getStudentInfo(orderInfo).map(this::buildStuPair);
    }

    private Optional<Map> getStudentInfo(OrderInfo orderInfo) {
        return Optional.ofNullable(orderInfo.getTcExtra())
                .map(extra -> extra.get(EDU_STUDENT_INFO))
                .map(studentInfoStr -> JsonUtil.readMap((String) studentInfoStr));
    }

    private Pair buildStuPair(Map studentInfo) {
        Long studentId = (Long) studentInfo.get(STUDENT_ID);
        Integer version = (Integer) studentInfo.get(VERSION);
        if (studentInfo.containsKey(ID_CARD_NO) && studentId != null && version != null) {
            return Pair.of(studentId, version);
        } else {
            return null;
        }
    }

    
}
