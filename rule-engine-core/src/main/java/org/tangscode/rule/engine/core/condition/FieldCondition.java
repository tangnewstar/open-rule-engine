package org.tangscode.rule.engine.core.condition;

import org.tangscode.rule.engine.core.RuleContext;
import org.tangscode.rule.engine.core.enums.ComparisonOperator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/17
 */
public class FieldCondition implements Condition {

    private String field;
    private ComparisonOperator operator;
    private Object threshold;

    public FieldCondition() {
    }

    public FieldCondition(String field, ComparisonOperator operator, Object threshold) {
        this.field = field;
        this.operator = operator;
        this.threshold = threshold;
    }

    @Override
    public boolean evaluate(RuleContext ruleContext) {
        Object paramValue = ruleContext.getParamThrowsIfNull(field);
        return operator.apply(paramValue, threshold);
    }

    @Override
    public Collection<String> involvingParams() {
        return Collections.singleton(field);
    }

    public String getField() {
        return field;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public Object getThreshold() {
        return threshold;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    public void setThreshold(Object threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "FieldCondition{" +
                "field='" + field + '\'' +
                ", operator=" + operator +
                ", threshold=" + threshold +
                '}';
    }
}
