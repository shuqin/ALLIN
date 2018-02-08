package zzz.study.patterns.composite.escondition;

import com.google.common.collect.Lists;

/**
 * Created by shuqin on 18/2/8.
 */
public class ComplexConditionTest {

  public static void main(String[] args) {
    Condition c1 = new EsCondition("shop_id", Op.eq, "55");
    Condition c2 = new EsCondition("order_no", Op.eq, "E2001");
    Condition c3 = new EsCondition("book_time", Op.range, new Range(15100000000L, 15200000000L));
    Condition c4 = new EsCondition("state", Op.in, Lists.newArrayList(5, 6));
    Condition c5 = new EsCondition("goods_title", Op.match, new Match("商品标题的一部分", "90%"));

    Condition c1mustc2mustc3 = c1.and(c2).and(c3);

    //System.out.println("c1 must c2 must c3 json: \n" + c1mustc2mustc3.json());
    System.out.println("c1 must c2 must c3 expr: \n" + c1mustc2mustc3.expr());

    Condition c1c2orc3c4 = c1.and(c2).or(c3.and(c4), 1);

    //System.out.println("( c1 must c2 )or( c3 must c4 ) json:\n" + c1c2orc3c4.json());
    System.out.println("\n( c1 must c2 )or( c3 must c4 ) expr:\n" + c1c2orc3c4.expr());

    Condition c2orc3mustc1orc4orc5 = (c2.or(c3, 1)).and(c4.or(Lists.newArrayList(c1,c5),2));

    //System.out.println("( c2 or c3 ) must ( c4 or c5 ) json:\n" + c2orc3mustc4orc5.json());
    System.out.println("\n( c2 or c3 ) must ( c1 or c4 or c5 ) expr:\n" + c2orc3mustc1orc4orc5.expr());

  }

}
