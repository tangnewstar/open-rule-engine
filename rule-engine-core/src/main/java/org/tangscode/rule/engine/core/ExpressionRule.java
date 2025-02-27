package org.tangscode.rule.engine.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.tangscode.rule.engine.core.expression.Expression;

import java.util.Collections;
import java.util.Set;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/27
 */
public class ExpressionRule implements SerializableRule {

    private final String id;
    private final Expression expression;

    private ExpressionRule(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @JsonCreator
    public static ExpressionRule create(
            @JsonProperty("id") String id,
            @JsonProperty("expression") Expression expression
    ) {
        return new ExpressionRule(id, expression);
    }

    @Override
    public Set<String> analyzeInvolvingParams() {
        return Collections.emptySet();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public Object evaluate(RuleContext context) {
        return expression.evaluate(context);
    }
}
