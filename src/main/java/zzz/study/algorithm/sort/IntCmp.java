package zzz.study.algorithm.sort;

public class IntCmp implements Cmp {

    public int cmp(Object o1, Object o2) {
        int i1 = ((Integer) o1).intValue();
        int i2 = ((Integer) o2).intValue();
        if (i1 < i2) {
            return -1;
        } else if (i1 == i2) {
            return 0;
        } else {
            return 1;
        }
    }

}
