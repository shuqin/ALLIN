package zzz.study.junitest3.sort;

public interface SortingStrategy {
	
	int[] sort(int[] list, int begin, int end);
	
    void sort(double[] list);
    <T> void sort(T[] list); 
}
