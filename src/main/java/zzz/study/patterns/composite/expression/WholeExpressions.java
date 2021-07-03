package zzz.study.patterns.composite.expression;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class WholeExpressions implements Expression {

    private List<Expression> expressions;

    public WholeExpressions() {
        this.expressions = new ArrayList<>();
    }

    public WholeExpressions(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public void addExpression(Expression expression) {
        this.expressions.add(expression);
    }

    public void addExpressions(List<Expression> expression) {
        this.expressions.addAll(expression);
    }

    public String getResult(Map<String, Object> valueMap) {
        for (Expression expression : expressions) {
            String result = expression.getResult(valueMap);
            if (StringUtils.isNotBlank(result)) {
                return result;
            }
        }
        return "";
    }

}
