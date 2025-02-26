package org.tangscode.rule.engine.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.tangscode.rule.engine.core.condition.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/21
 */
// 唯一实现类（通过泛型支持不同类型规则）
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleRule.class, name = "rule")
})
@JsonSerialize(as = SimpleRule.class)
@JsonDeserialize(as = SimpleRule.class)
public final class SimpleRule<T> implements Rule {
    private final String id;
    private final Predicate<RuleContext> condition;
    private final Function<RuleContext, T> action;
    private final T defaultValue;

    public SimpleRule(String id, Predicate<RuleContext> condition, Function<RuleContext, T> action, T defaultValue) {
        this.id = id;
        this.condition = condition;
        this.action = action;
        this.defaultValue = defaultValue;
    }

    @JsonCreator
    public static <T> SimpleRule<T> create(
            @JsonProperty("id") String id,
            @JsonProperty("when") Condition condition,
            @JsonProperty("then") Action<T> action,
            @JsonProperty("default") T defaultValue) {
        return new SimpleRule<>(
                id,
                condition::evaluate,
                action::execute,
                defaultValue
        );
    }

    // 流式构建方法（支持不同类型）
    public static <T> Builder<T> define(String id) {
        return new Builder<>(id);
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
    public T evaluate(RuleContext ctx) {
        return condition.test(ctx) ? action.apply(ctx) : defaultValue;
    }

    // 带泛型的构建器
    public static class Builder<T> {
        private final String id;
        private Predicate<RuleContext> condition;
        private Function<RuleContext, T> action;
        private T defaultValue;

        public Builder(String id) {
            this.id = id;
            this.defaultValue = null;
        }

        public Builder<T> when(Predicate<RuleContext> condition) {
            this.condition = condition;
            return this;
        }

        public Builder<T> defaultValue(T value) {
            this.defaultValue = value;
            return this;
        }

        public Builder<T> then(Function<RuleContext, T> action) {
            this.action = action;
            return this;
        }

        public SimpleRule build() {
            return new SimpleRule(id, condition, action, defaultValue);
        }

//        // 条件-动作对封装
//        private static class ConditionActionPair<T> {
//            final Predicate<RuleContext> condition;
//            final Function<RuleContext, T> action;
//
//            ConditionActionPair(Predicate<RuleContext> condition, Function<RuleContext, T> action) {
//                this.condition = condition;
//                this.action = action;
//            }
//
//            static class ConditionActionPairBuilder<T> {
//                private Predicate<RuleContext> condition;
//                private Function<RuleContext, T> action;
//                public ConditionActionPairBuilder() {}
//
//                public ConditionActionPairBuilder<T> when(Predicate<RuleContext> condition) {
//                    this.condition = condition;
//                    return this;
//                }
//
//                public ConditionActionPair<T> then(Function<RuleContext, T> action) {
//                    return new ConditionActionPair<>(condition, action);
//                }
//            }
//        }
    }

}

