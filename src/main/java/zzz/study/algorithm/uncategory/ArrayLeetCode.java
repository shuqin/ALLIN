package zzz.study.algorithm.uncategory;

public class ArrayLeetCode {

    public static void main(String[] args) {
        assert removeElement(new int[]{1}, 1) == 0;
        assert removeElement(new int[]{1, 2, 3, 1}, 1) == 2;
        assert removeElement(new int[]{2, 1, 3, 5}, 1) == 3;
        assert removeElement(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2) == 5;
    }

    public static int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int j = nums.length - 1;
        int i = 0;
        for (; i < nums.length; i++) {
            if (nums[i] == val) {
                while (j >= 0 && nums[j] == val) {
                    j--;
                }
                if (i >= j) {
                    break;
                }
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                j--;
            }
        }
        return j + 1;
    }
}
