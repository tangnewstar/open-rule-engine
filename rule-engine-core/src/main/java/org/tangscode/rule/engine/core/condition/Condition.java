package org.tangscode.rule.engine.core.condition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.tangscode.rule.engine.core.RuleContext;

import java.util.Collection;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/17
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LogicalCondition.class, name = "LOGICAL"),
        @JsonSubTypes.Type(value = FieldCondition.class, name = "FIELD"),
        @JsonSubTypes.Type(value = TrueCondition.class, name = "TRUE")
})
public interface Condition {
    boolean evaluate(RuleContext ruleContext);
    Collection<String> involvingParams();
}
