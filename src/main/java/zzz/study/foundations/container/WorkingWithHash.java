package zzz.study.foundations.container;

import java.util.*;

public class WorkingWithHash {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        System.out.println("map: " + map);
        System.out.println("A ---> " + map.get("A"));
        System.out.println("a ---> " + map.get("a"));

        // 重复关键字插入，将关键字的新值取代之前的原值
        map.put("A", 5);
        System.out.println("map: " + map);

        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList("I am am an an Excellent boy.".split(" ")));
        System.out.println("set: " + set);

        System.out.println("**********************************");

        Map<Perl, Integer> map2 = new HashMap<Perl, Integer>();
        map2.put(new Perl("1.0", 100), 1);
        map2.put(new Perl("2.0", 2000), 2);
        map2.put(new Perl("3.0", 30000), 3);
        map2.put(new Perl("4.0", 76000), 4);
        map2.put(new Perl("5.0", 500000), 5);
        System.out.println(map2);
        Perl key = new Perl("3.0", 30000);
        System.out.println(key + " ---> " + map2.get(key));
        Perl key2 = new Perl("3.0", 3000);
        System.out.println(key2 + " ---> " + map2.get(key2));

        Set<Perl> set2 = new HashSet<Perl>();
        set2.add(new Perl("1.0", 100));
        set2.add(new Perl("2.0", 2000));
        set2.add(new Perl("2.0", 2000));
        set2.add(new Perl("3.0", 30000));
        set2.add(new Perl("3.0", 30000));
        set2.add(new Perl("4.0", 76000));
        System.out.println("set2: " + set2);
    }

}

class Perl {

    private String version;
    private int modNum;

    public Perl(String version, int modNum) {
        this.version = version;
        this.modNum = modNum;
    }

    public String toString() {
        return "(" + version + " " + modNum + ")";
    }

    public boolean equals(Object obj) {
        return obj instanceof Perl &&
                ((Perl) obj).version.equals(version) && ((Perl) obj).modNum == modNum;
    }

    public int hashCode() {
        int result = 17;
        result += version.hashCode();
        result += modNum;
        return result;
    }

}

/**
 * Note:
 * 自定义类要想与 Hash 集合 (HashMap, HashSet, LinkedHashMap, LinkedHashSet) 良好协作，
 * 必须仔细覆写 equals 和 hashCode 方法。
 */
