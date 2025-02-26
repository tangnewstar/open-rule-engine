package org.tangscode.rule.engine.core.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.tangscode.rule.engine.core.RuleContext;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/20
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConstantExpression.class, name = "CONSTANT"),
        @JsonSubTypes.Type(value = ScriptExpression.class, name = "SCRIPT")
})
public interface Expression {
    // 计算
    Object evaluate(RuleContext context);
}
