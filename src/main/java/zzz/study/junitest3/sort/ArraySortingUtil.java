package zzz.study.junitest3.sort;

public class ArraySortingUtil {

    private SortingStrategy sortingStrategy;

    public ArraySortingUtil() {
        if (sortingStrategy == null) {
            sortingStrategy = new InsertSortStrategy();
        }
    }

    public ArraySortingUtil(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    /*
     *  对 sort[start:end] 进行非降排序，包括  start 端点但不包括 end 端点。
     */
    public int[] sort(int[] unsorted, int start, int end) throws Exception {
        validateParam(unsorted, start, end);
        return sortingStrategy.sort(unsorted, start, end);
    }

    public int[] sort(int[] unsorted, int start) throws Exception {
        checkNullOrEmpty(unsorted);
        return sort(unsorted, start, unsorted.length);
    }

    public int[] sort(int[] unsorted) throws Exception {
        checkNullOrEmpty(unsorted);
        return sort(unsorted, 0, unsorted.length);
    }

    public double[] sort(double[] unsorted) {

        double[] unsortedCopy = new double[unsorted.length];
        for (int i = 0; i < unsorted.length; i++) {
            unsortedCopy[i] = unsorted[i];
        }
        sortingStrategy.sort(unsortedCopy);
        return unsortedCopy;
    }

    public <T> T[] sort(T[] unsorted) {

        T[] unsortedCopy = (T[]) unsorted.clone();
        sortingStrategy.sort(unsortedCopy);
        return unsortedCopy;
    }

    public void checkNullOrEmpty(int[] unsorted) throws Exception {
        if (unsorted == null || unsorted.length == 0)
            throw new Exception("Array Null or Empty");
    }

    public void validateParam(int[] unsorted, int begin, int end) throws Exception {
        if (begin < 0 || begin >= unsorted.length)
            throw new Exception("Passed Parameters Error");
        if (end < 0 || end > unsorted.length)
            throw new Exception("Passed Parameters Error");
        if (begin >= end)
            throw new Exception("Passed Parameters Error");
    }

}
