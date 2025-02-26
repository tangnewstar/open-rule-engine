package org.tangscode.rule.engine.core.expression;

import org.tangscode.rule.engine.core.RuleContext;

import java.util.function.Function;

/**
 * @author tangxinxing
 * @version 1.0
 * @description 仅用于流式API
 * @date 2025/2/20
 */
public class LambdaExpression implements Expression{
    private Function<RuleContext, Object> function;
    public LambdaExpression(Function<RuleContext, Object> function) {
        this.function = function;
    }

    @Override
    public Object evaluate(RuleContext context) {
        return function.apply(context);
    }

}
