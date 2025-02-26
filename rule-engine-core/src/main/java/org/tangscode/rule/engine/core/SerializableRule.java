package org.tangscode.rule.engine.core;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Set;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/21
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type", defaultImpl = ValueRule.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ValueRule.class, name = "VALUE")
})
public interface SerializableRule extends Rule, Serializable {
    Set<String> analyzeInvolvingParams();
}
