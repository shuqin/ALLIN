package zzz.study.algorithm.dynamicplan;

/**
 * 字节面试题
 * 有一个日志文件，记录用户登录抖音、登出抖音的时间，求每一时刻在线人数
 * 格式为uid login_time logout_time
 * 输入: logs = [[1, 0, 5], [2, 0, 6], [3, 0, 3], [4, 1, 2], [5, 1, 3], [6, 2, 3], [7, 3, 4], [8, 4, 6]]
 * 输出: [3, 5, 5, 3, 3, 2, 0]
 */
public class OnlinePeople {

    public static void main(String[] args) {
        int[][] logs = new int[][] { {1, 0, 5}, {2, 0, 6}, {3, 0, 3}, {4, 1, 2}, {5, 1, 3}, {6, 2, 3}, {7, 3, 4}, {8, 4, 6} };
        print(countAllByPlain(logs));
        print(countAllByDyPlan(logs));
    }

    public static int[] countAllByDyPlan(int[][] logs) {
        int maxTime = maxTime(logs);
        int[] res = new int[maxTime];

        for (int[] log: logs) {
            int start = log[1];
            int end = log[2];
            res[start] = res[start]+1;

        }
        return res;
    }

    public static int[] countAllByPlain(int[][] logs) {
        int maxTime = maxTime(logs);
        int[] res = new int[maxTime];
        count(res, logs);
        return res;
    }

    public static void print(int[] res) {
        for (int i: res) {
            System.out.println(i);
        }
    }

    public static void count(int[] res, int[][] logs) {
        for (int[] row: logs) {
            count(res, row[1], row[2]);
        }
    }

    public static void count(int[] res, int start, int end) {
        for (int i=start; i < end; i++) {
            res[i] = res[i] + 1;
        }
    }

    public static int maxTime(int[][] logs) {
        int max = 0;
        for (int[] row: logs) {
            if (row[2] >= max) {
                max = row[2];
            }
        }
        return max;
    }
}
