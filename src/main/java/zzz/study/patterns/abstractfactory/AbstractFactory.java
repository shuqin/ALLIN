package zzz.study.patterns.abstractfactory;

/**
 * 抽象工厂：
 * 1. 对能够生产各种抽象产品的工厂的抽象
 * 2. 每种抽象产品都有多种具体产品实现
 * 3. 每种具体工厂均能根据需求生产相应的具体产品
 */
public interface AbstractFactory {

    public IPad createIPad();

    public IMac createIMac();
}
