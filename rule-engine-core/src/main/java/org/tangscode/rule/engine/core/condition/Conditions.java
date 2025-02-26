package org.tangscode.rule.engine.core.condition;

import org.tangscode.rule.engine.core.enums.ComparisonOperator;
import org.tangscode.rule.engine.core.enums.LogicOperator;

import java.util.Arrays;
import java.util.List;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/21
 */
public class Conditions {

    public static Condition field(String key, ComparisonOperator operator, Object threshold) {
        return new FieldCondition(key, operator, threshold);
    }

    public static Condition and(Condition conditionA, Condition conditionB) {
        return and(Arrays.asList(conditionA, conditionB));
    }

    public static Condition and(List<Condition> conditions) {
        return new LogicalCondition(LogicOperator.AND, conditions);
    }

    public static Condition or(Condition conditionA, Condition conditionB) {
        return or(Arrays.asList(conditionA, conditionB));
    }

    public static Condition or(List<Condition> conditions) {
        return new LogicalCondition(LogicOperator.OR, conditions);
    }
}
