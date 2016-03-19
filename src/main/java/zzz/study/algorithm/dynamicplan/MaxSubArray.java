package zzz.study.algorithm.dynamicplan;

public class MaxSubArray {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{1, 3, -5, 7, 9, 8, -6}));
    }

    public static int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

}
