package cc.lovesq.study.test.algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static junit.framework.Assert.assertTrue;
import static zzz.study.algorithm.select.RandomSelector.*;

public class RandomSelectorTester {

    public static void testBigRandInt() {

        System.out.println("--------------- �����ɴ����� ----------------");

        for (int bits = 0; bits <= 32; bits += 16) {
            testBigRandInt(bits, 20);
        }
        testBigRandInt(-1, 20);
        testBigRandInt(1, 20);
        testBigRandInt(31, 20);
    }

    private static void testBigRandInt(int bits, int n) {

        System.out.printf("��� %d �������Ʊ�ʾλ����� %d λ�� ������: \n", n, bits);
        for (int i = 0; i < n; i++) {
            try {
                int randInt = bigRandInt(bits);
                System.out.println(randInt);
                assertTrue(getBits(bigRandInt(bits)) >= bits);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private static int getBits(int i) {
        return Integer.toBinaryString(i).length();
    }

    public static void testRandRange() {

        System.out.println("**************** ��ɸ�Χ��������� ****************");

        for (int low = -50; low <= 50; low += 20) {
            testRandRange(low, 30, 20);
        }
    }

    private static void testRandRange(int low, int high, int n) {

        System.out.printf("������ %d ����Χ [%d,%d) ������: \n", n, low, high);
        for (int i = 0; i < n; i++) {
            try {
                int randInt = randRange(low, high);
                System.out.printf(randInt + ((i % n == n - 1) ? "\n" : " "));
                assertTrue(randInt >= low && randInt < high);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }

    }


    public static void testSelectMOrdered3() {
        System.out.println("********** ��ָ��������������ָ����Ŀ�����������б� ************");
        System.out.println("********** selectMOrderedRandInts3 ************");

        for (int n = 10; n <= 10000; n *= 10)
            for (int m = 0; m <= 20; m += 10) {
                try {
                    System.out.printf("�� [0,%d) ��ѡ�� %d ���������������:\n", n, m);
                    int[] result = selectMOrderedRandInts3(m, n);
                    testIntCorrectness(result, n);
                    printArray(result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    public static void testSelectMOrdered2() {
        System.out.println("********** ��ָ��������������ָ����Ŀ�����������б� ************");
        System.out.println("********** selectMOrderedRandInts2 ************");

        for (int n = 10; n <= 10000; n *= 10)
            for (int m = 0; m <= 20; m += 10) {
                try {
                    System.out.printf("�� [0,%d) ��ѡ�� %d ���������������:\n", n, m);
                    int[] result = selectMOrderedRandInts2(m, n);
                    testIntCorrectness(result, n);
                    printArray(result);
                    ;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    public static void testSelectMOrdered() {
        System.out.println("********** ��ָ��������������ָ����Ŀ�����������б� ************");
        System.out.println("********** selectMOrderedRandInts ************");

        for (int n = 10; n <= 10000; n *= 10)
            for (int m = 0; m <= 20; m += 10) {
                try {
                    System.out.printf("�� [0,%d) ��ѡ�� %d ���������������:\n", n, m);
                    int[] result = selectMOrderedRandInts(m, n);
                    testIntCorrectness(result, n);
                    printArray(result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    public static void testSelectMDisordered() {

    }

    public static void testSelectMDisordered2() {
        System.out.println("********** ��ָ��������������ָ����Ŀ�����������б� ************");

        for (int n = 10; n <= 10000; n *= 10)
            for (int m = 0; m <= 20; m += 10) {
                try {
                    System.out.printf("�� [0,%d) ��ѡ�� %d ���������������:\n", n, m);
                    int[] result = selectMDisorderedRandInts2(m, n);
                    testIntCorrectness(result, n);
                    printArray(result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    public static void testSelectMDisordered3() {
        System.out.println("********** ��ָ��������������ָ����Ŀ�����������б� ************");

        for (int n = 10; n <= 10000; n *= 10)
            for (int m = 0; m <= 20; m += 10) {
                try {
                    System.out.printf("�� [0,%d) ��ѡ�� %d ���������������:\n", n, m);
                    int[] result = selectMDisorderedRandInts3(m, n);
                    testIntCorrectness(result, n);
                    printArray(result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    /*
     * ���Ը������е�����Ԫ���Ƿ�����[0:n-1]�ķ�Χ��
     */
    private static void testIntCorrectness(int[] arr, int n) {
        for (int i : arr) {
            assertTrue(i >= 0 && i < n);
        }
    }

    public static void menu() {
        System.out.println("------------------ �������ѡ����� --------------------");
        System.out.println("(1) testSelectMOrdered (2) testSelectMOrdered2 (3) testSelectMOrdered3");
        System.out.println("(4) testSelectMDisordered  (5) testSelectMDisorder2 (6) testSelectMDisorder3");
        System.out.println("(7) testBigRandInt  (8) testRandRange ");
        System.out.println("(9) show menu (0) quit");
    }

    public static void test() {
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
            String input = "";
            try {
                menu();
                while ((input = bReader.readLine()) != null && input.length() != 0) {
                    if (input.matches("\\s*[0-9]\\s*")) {
                        int choice = Integer.parseInt(input);
                        switch (choice) {
                            case 0:
                                break;
                            case 1:
                                testSelectMOrdered();
                                break;
                            case 2:
                                testSelectMOrdered2();
                                break;
                            case 3:
                                testSelectMOrdered3();
                                break;
                            case 4:
                                testSelectMDisordered();
                                break;
                            case 5:
                                testSelectMDisordered2();
                                break;
                            case 6:
                                testSelectMDisordered3();
                                break;
                            case 7:
                                testBigRandInt();
                                break;
                            case 8:
                                testRandRange();
                                break;
                            case 9:
                                menu();
                                break;
                            default:
                                break;
                        }
                    } else {
                        System.out.println("����������������� 0-8 ��");
                    }
                }
            } finally {
                bReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        test();

    }


}
