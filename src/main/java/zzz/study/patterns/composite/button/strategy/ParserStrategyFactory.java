package zzz.study.patterns.composite.button.strategy;

public class ParserStrategyFactory {

    public ConditionParserStrategy getParser(String format) {
        if ("json".equals(format)) {
            return new JSONStrategy();
        }
        return new DomainStrategy();
    }

}
