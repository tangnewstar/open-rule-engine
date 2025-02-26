package org.tangscode.rule.engine.core.expression;

import org.tangscode.rule.engine.core.RuleContext;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/20
 */
public class ConstantExpression implements Expression{

    private Object value;

    public ConstantExpression() {
    }

    public ConstantExpression(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(RuleContext context) {
        return value;
    }
}
