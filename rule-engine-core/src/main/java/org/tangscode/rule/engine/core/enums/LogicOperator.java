package org.tangscode.rule.engine.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * @author tangxinxing
 * @version 1.0
 * @description 逻辑操作符
 * @date 2025/2/17
 */
public enum LogicOperator {
    AND("AND") {
        @Override
        public boolean apply(List<Boolean> conditions) {
            if (conditions == null || conditions.isEmpty()) {
                throw new IllegalArgumentException("AND operator requires at least one condition");
            }
            return conditions.stream().allMatch(Boolean::booleanValue);
        }
    },
    OR("OR") {
        @Override
        public boolean apply(List<Boolean> conditions) {
            if (conditions == null || conditions.isEmpty()) {
                throw new IllegalArgumentException("OR operator requires at least one condition");
            }
            return conditions.stream().anyMatch(Boolean::booleanValue);
        }
    },
    NOT("NOT") {
        @Override
        public boolean apply(List<Boolean> conditions) {
            if (conditions == null || conditions.size() != 1) {
                throw new IllegalArgumentException("NOT operator requires exactly one condition");
            }
            return !conditions.get(0);
        }
    };
    private final String symbol;
    LogicOperator(String symbol) {
        this.symbol = symbol;
    }

    @JsonCreator
    public static LogicOperator fromString(String symbol) {
        for (LogicOperator operator : values()) {
            if (operator.symbol.equalsIgnoreCase(symbol)) {
                return operator;
            }
        }
        throw new IllegalArgumentException("Unsupported operator: " + symbol);
    }

    @JsonValue
    public String getSymbol() {
        return symbol;
    }

    public abstract boolean apply(List<Boolean> conditions);
}
