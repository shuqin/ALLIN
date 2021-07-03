package zzz.study.algorithm.dynamicplan;

public class Knapsack {

    /**
     * 背包重量
     */
    private int weight;

    /**
     * 背包物品价值
     */
    private int value;

    /***
     * 构造器
     */
    public Knapsack(int weight, int value) {
        this.value = value;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return "[weight: " + weight + " " + "value: " + value + "]";
    }

}
