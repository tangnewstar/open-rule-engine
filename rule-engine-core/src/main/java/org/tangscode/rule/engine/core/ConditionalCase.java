package org.tangscode.rule.engine.core;

import org.tangscode.rule.engine.core.condition.Condition;
import org.tangscode.rule.engine.core.expression.Expression;
import org.tangscode.rule.engine.core.expression.LambdaExpression;
import org.tangscode.rule.engine.core.expression.ScriptExpression;

import java.util.function.Function;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/20
 */
// 分支条件单元（含防御性校验）
public class ConditionalCase {

    private Condition condition;
    private Expression expression;

    public Condition getCondition() {
        return condition;
    }

    public Expression getExpression() {
        return expression;
    }

    private void setCondition(Condition condition) {
        this.condition = condition;
    }

    private void setExpression(Expression expression) {
        this.expression = expression;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Condition condition;
        private Expression expression;

        public ConditionalCase build() {
            validate();
            ConditionalCase cc = new ConditionalCase();
            cc.setCondition(condition);
            cc.setExpression(expression);
            return cc;
        }

        public Builder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public Builder expression(Expression expression) {
            this.expression = expression;
            return this;
        }

        // Builder校验逻辑优化
        private void validate() {
            if (condition == null) {
                throw new IllegalArgumentException("Condition不能为空");
            }
            if (expression == null) {
                throw new IllegalArgumentException("Expression不能为空");
            }
        }
    }

    // 流畅API
    public ConditionalCase when(Condition condition) {
        this.condition = condition;
        return this;
    }

    public ConditionalCase then(Function<RuleContext, Object> lambda) {
        this.expression = Expressions.lambda(lambda);
        return this;
    }

    public static class Expressions {
        // build lambda expression
        public static Expression lambda(Function<RuleContext, Object> lambda) {
            return new LambdaExpression(lambda);
        }
        // build script expression
        public static Expression script(String lang, String script) {
            return new ScriptExpression(lang, script);
        }
    }
}

