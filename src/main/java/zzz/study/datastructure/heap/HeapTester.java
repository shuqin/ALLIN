package zzz.study.datastructure.heap;

import java.util.Arrays;

public class HeapTester {

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(10);
        System.out.println("堆长度 : " + maxHeap.size());
        int[] datas = new int[]{11, 2, 9, 7, 42, 8, 6, 21};
        for (int data : datas) {
            try {
                maxHeap.insert(data);
                System.out.printf("将元素 %d 插入堆之后: \t", data);
                System.out.println(maxHeap);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            maxHeap.insert(12);
            maxHeap.insert(15);
            maxHeap.insert(18);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("最终堆的内容: " + maxHeap);

        for (int i = 1; i <= maxHeap.size(); i++) {
            try {
                System.out.println("delete : " + maxHeap.delete());
                System.out.println("删除元素后堆的内容 : " + maxHeap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        System.out.println("**************** 堆排序 *******************");
        int[] unsorted = new int[]{34, 12, 27, 58, 9, 41, 80, 98};
        MaxHeap heap = new MaxHeap(datas.length);
        try {
            int[] sorted = heap.heapsort(unsorted);
            System.out.println("排序前: " + Arrays.toString(unsorted));
            System.out.println("排序后: " + Arrays.toString(sorted));
        } catch (Exception e) {
            System.out.println("排序失败！");
            e.printStackTrace();
        }

    }

}
