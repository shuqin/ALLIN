package zzz.study.algorithm.permutation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * PowerSet
 * Generate all the subsets of a given set.
 * <p>
 * 生成给定集合的所有子集。
 */

public class PowerSet {

    /**
     * 每一个子集使用一个 LinkedList<Integer> 来存储,
     * 使用 LinkedList<Integer> 的 列表来存储所有的子集
     */

    private ArrayList<LinkedList<Integer>> powerset;

    /**
     * 构造器
     */

    public PowerSet() {

        if (powerset == null)
            powerset = new ArrayList<LinkedList<Integer>>();

    }

    /**
     * 生成  {1,2,3,……, n} 的 幂集
     */

    private void createPowerset(int n) {

        if (n < 0)
            throw new IllegalArgumentException();

        if (n == 0) {
            LinkedList<Integer> empty = new LinkedList<Integer>();
            powerset.add(empty);
        }

        if (n == 1) {
            LinkedList<Integer> empty = new LinkedList<Integer>();
            LinkedList<Integer> init = new LinkedList<Integer>();
            init.add(1);
            powerset.add(empty);
            powerset.add(init);
        } else {
            createPowerset(n - 1);

            // powerset(n) = powerset(n-1) + powerset(n-1)∪ {n}

            int length = powerset.size();
            for (int i = 0; i < length; i++) {
                LinkedList<Integer> p = powerset.get(i);
                LinkedList<Integer> pcopy = copylist(p);
                pcopy.add(p.size(), n);
                powerset.add(pcopy);

            }

        }

    }

    /**
     * 获取 n 个元素集合的幂集
     */

    public ArrayList<LinkedList<Integer>> getPowerset(int n) {

        createPowerset(n);
        return powerset;
    }


    /**
     * 复制 list 的元素到另一个列表， 并返回该列表
     */

    private LinkedList<Integer> copylist(LinkedList<Integer> list) {

        LinkedList<Integer> copylist = new LinkedList<Integer>();
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            Integer i = (Integer) iter.next();
            copylist.add(i);
        }
        return copylist;
    }


}
