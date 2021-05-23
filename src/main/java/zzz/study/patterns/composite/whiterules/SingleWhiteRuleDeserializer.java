package zzz.study.patterns.composite.whiterules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @Description TODO
 * @Date 2021/5/22 1:31 下午
 * @Created by qinshu
 */
public class SingleWhiteRuleDeserializer extends JsonDeserializer<FunctionWhiteRule> {

    @Override
    public FunctionWhiteRule deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        return null;
    }
}
