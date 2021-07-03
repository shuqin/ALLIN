package zzz.study.algorithm.mulstrmatch;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 多字符串模式匹配问题
 */
public class MulStringMatchProblem {

    MulStringPattern[] msps;

    public MulStringMatchProblem(MulStringPattern[] msps) {
        this.msps = msps;
    }

    public static void main(String[] args) {
        MulStringPattern[] msps = new MulStringPattern[]{
                new MulStringPattern("A/B"),
                new MulStringPattern("A/C"),
                new MulStringPattern("A/B/C"),
                new MulStringPattern("B/C/E"),
                new MulStringPattern("D/C/F"),
                new MulStringPattern("F/G"),
                new MulStringPattern("F/G/H"),
                new MulStringPattern("A/C/E/G")
        };
        MulStringMatchProblem msmp = new MulStringMatchProblem(msps);
        msmp.testFunction();
    }

    /**
     * 最简单的方案： 逐一匹配
     */
    public List<MulStringPattern> simpleMatch(String sequence) {
        System.out.println("sequence: " + sequence);
        if (sequence == null) {
            return null;
        }
        List<MulStringPattern> result = new ArrayList<MulStringPattern>();
        for (MulStringPattern msp : msps) {
            if (Pattern.matches(msp.getPattern(), sequence)) {
                result.add(msp);
            }
        }
        return result;
    }

    public void testSimpleMatch() {
        List<MulStringPattern> result = simpleMatch("ABCDEFG");
        System.out.println("result: " + result);
        result = simpleMatch("");
        System.out.println("result: " + result);
        result = simpleMatch(null);
        System.out.println("result: " + result);
        result = simpleMatch("BCEFGH");
        System.out.println("result: " + result);
    }

    public void testFunction() {
        testSimpleMatch();
    }

}
