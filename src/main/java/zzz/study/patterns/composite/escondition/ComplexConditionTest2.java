package zzz.study.patterns.composite.escondition;

import com.google.common.collect.Lists;

import static zzz.study.patterns.composite.escondition.ConditionFactory.*;

/**
 * Created by shuqin on 18/2/8.
 */
public class ComplexConditionTest2 {

    public static void main(String[] args) {
        Condition c1 = eq("shop_id", "55");
        Condition c2 = eq("order_no", "E2001");
        Condition c3 = range("book_time", new Range(15100000000L, 15200000000L));
        Condition c4 = in("state", Lists.newArrayList(5, 6));
        Condition c5 = match("goods_title", new Match("商品标题的一部分", "90%"));

        Condition c1mustc2mustc3 = c1.and(c2).and(c3);

        //System.out.println("c1 must c2 must c3 json: \n" + c1mustc2mustc3.json());
        System.out.println("c1 must c2 must c3 expr: \n" + c1mustc2mustc3.expr());

        Condition c1c2orc3c4 = c1.and(c2).or(c3.and(c4), 1);

        //System.out.println("( c1 must c2 )or( c3 must c4 ) json:\n" + c1c2orc3c4.json());
        System.out.println("\n( c1 must c2 )or( c3 must c4 ) expr:\n" + c1c2orc3c4.expr());

        Condition c2orc3mustc1orc4orc5 = (c2.or(c3, 1)).and(c4.or(Lists.newArrayList(c1, c5), 2));

        //System.out.println("( c2 or c3 ) must ( c4 or c5 ) json:\n" + c2orc3mustc4orc5.json());
        System.out.println("\n( c2 or c3 ) must ( c1 or c4 or c5 ) expr:\n" + c2orc3mustc1orc4orc5.expr());

        Condition complexCond = ((c1.and(c2)).or(c3.and(c4), 1)).and(c5.or(Lists.newArrayList(c2, c3), 2));
        System.out.println("\n(( c1 must c2 ) or ( c3 must c4 )) must (c5 or c2 or c3) expr:\n" + complexCond.expr());

    }

}
