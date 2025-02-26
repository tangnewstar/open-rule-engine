package org.tangscode.rule.engine.core.condition;

import org.tangscode.rule.engine.core.enums.ComparisonOperator;

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


}
