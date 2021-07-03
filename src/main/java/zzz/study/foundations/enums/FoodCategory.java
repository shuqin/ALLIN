package zzz.study.foundations.enums;

/**
 * 实现枚举实例的组织、分类和随机选取功能
 */

public enum FoodCategory {

    Fruit(Eatable.Fruit.class), Vegetable(Eatable.Vegetable.class);

    private Eatable[] values;

    private FoodCategory(Class<? extends Eatable> kind) {
        values = kind.getEnumConstants();
    }

    public Generator<Eatable> getRandomSelector() {
        return new RandomSelector();
    }

    interface Eatable {

        enum Fruit implements Eatable {
            APPLE, BANANA, PEAR, GRAPE, WATERMELON, TOMATO;
        }

        enum Vegetable implements Eatable {
            CABBAGE, POTATO, EGG;
        }
    }

    private class RandomSelector implements Generator<Eatable> {

        public Eatable randomSelect() {
            return Enums.random(values);
        }

    }

}

