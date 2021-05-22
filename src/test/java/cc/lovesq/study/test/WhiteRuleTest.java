package cc.lovesq.study.test;

import cc.lovesq.util.GsonUtil;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;
import shared.utils.JsonUtil;
import zzz.study.patterns.composite.whiterules.*;

import java.util.Arrays;

/**
 * @Description TODO
 * @Date 2021/5/8 4:44 下午
 * @Created by qinshu
 */
public class WhiteRuleTest {

    @Test
    public void testFuncRule() {
        FunctionWhiteRule fwr = new FunctionWhiteRule("cc.lovesq.utils.AgeUtil#inc", new RangeRule("age", 30, 36));
        Person p = new Person("qin", 32);
        fwr.test(p);

        String json = JSON.toJSONString(fwr);

        FunctionWhiteRule fwr2 = JsonUtil.toObject(json, FunctionWhiteRule.class);
        fwr2.test(p);

        //FunctionWhiteRule fwr3 = GsonUtil.fromJson(json, FunctionWhiteRule.class);
        //fwr3.test(p);
    }

    @Test
    public void testAnd() {
        AndRule andRule = new AndRule(
                new InRule("ip", Arrays.asList("localhost", "127.0.0.1")),
                new EqualsRule("md5", "qwertyuiop"),
                new MatchRule("number", Arrays.asList("\\d+"))
        );
        DetectEntity detectEntity = new DetectEntity("localhost", "qwertyuiop", "123");
        Assert.assertTrue(andRule.test(detectEntity));

        String andExpr = andRule.expr();
        System.out.println(andExpr);
        WhiteRule rule = WhiteRule.parse(andExpr);
        Assert.assertTrue(rule.test(detectEntity));
    }

    @Test
    public void testOr() {
        AndRule orRule = new AndRule(
                new InRule("ip", Arrays.asList("localhost", "127.0.0.1")),
                new EqualsRule("md5", "qwertyuiop"),
                new RangeRule("number", "1609484400", "1609491600")
        );
        DetectEntity detectEntity2 = new DetectEntity("152.15.0.0", "haha", "1509484400");
        Assert.assertFalse(orRule.test(detectEntity2));

        String orExpr = orRule.expr();
        System.out.println(orExpr);
        WhiteRule rule = WhiteRule.parse(orExpr);

        Assert.assertFalse(rule.test(detectEntity2));
    }
}

@Getter
@Setter
class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class DetectEntity {

    private String ip;
    private String md5;
    private String number;

    public DetectEntity(String ip, String md5, String number) {
        this.ip = ip;
        this.md5 = md5;
        this.number = number;
    }
}
