package org.tangscode.rule.engine.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.tangscode.rule.engine.core.condition.Condition;
import org.tangscode.rule.engine.core.expression.Expression;
import org.tangscode.rule.engine.core.expression.ConstantExpression;

import java.util.*;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/21
 */
// 返回值的规则定义（支持序列化）
public class ValueRule<T> implements SerializableRule {

    private final String id;
    private final List<RuleBranch<T>> branches;
    private final T defaultValue;

    private ValueRule(String id, List<RuleBranch<T>> branches, T defaultValue) {
        this.id = Objects.requireNonNull(id, "Rule ID cannot be null");
        if (branches == null || branches.isEmpty()) {
            throw new IllegalArgumentException("Rule branches cannot be null");
        }
        this.branches = branches;
        this.defaultValue = defaultValue; // 允许defaultValue为null
    }

    // 修改构造方法参数接收逻辑 ▼
    @JsonCreator
    public static <T> ValueRule<T> create(
            @JsonProperty("id") String id,
            @JsonProperty(value = "branches", required = false) List<RuleBranch<T>> branches,
            @JsonProperty(value = "defaultValue", required = false) T defaultValue
    ) {
        return new ValueRule<>(id, branches, defaultValue);
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
        // 遍历分支进行规则计算
        for (RuleBranch branch: branches) {
            if (branch.condition.evaluate(context)) {
                return branch.expression.evaluate(context);
            }
        }
        return defaultValue;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    @Override
    public Set<String> analyzeInvolvingParams() {
        Set<String> paramSet = new HashSet<>();
        for (RuleBranch branch: branches) {
            paramSet.addAll(branch.condition.involvingParams());
        }

        return paramSet;
    }

    // Builder class for constructing ValueRule instances
    public static class Builder<T> {
        private String id;
        private List<RuleBranch<T>> branches = new ArrayList<>();
        private T defaultValue;

        public Builder<T> id(String id) {
            this.id = id;
            return this;
        }

        public Builder<T> defaultValue(T defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        // 新增的DSL方法
        public BranchBuilder when(Condition condition) {
            return new BranchBuilder(this, condition);
        }

        public ValueRule<T> build() {
            return new ValueRule<>(id, branches, defaultValue);
        }
    }

    // 独立分支定义（支持嵌套复杂条件）
    public static class RuleBranch<T> {

        @JsonProperty("condition")
        private final Condition condition;
        @JsonProperty("expression")
        private final Expression expression;

        // 支持Jackson反序列化
        @JsonCreator
        public RuleBranch(
                @JsonProperty("condition") Condition condition,
                @JsonProperty("expression") Expression expression
        ) {
            this.condition = condition;
            this.expression = expression;
        }
    }

    // 分支构建器
    public static class BranchBuilder {
        private final Builder parentBuilder;
        private final Condition condition;

        BranchBuilder(Builder builder, Condition condition) {
            this.parentBuilder = builder;
            this.condition = condition;
        }

        public Builder then(Object value) {
            return then(new ConstantExpression(value));
        }

        public Builder then(Expression expression) {
            parentBuilder.branches.add(new RuleBranch<>( condition, expression));
            return parentBuilder;
        }
    }
}

