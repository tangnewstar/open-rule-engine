package org.tangscode.rule.engine.core.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tangscode.rule.engine.core.RuleContext;
import org.tangscode.rule.engine.core.enums.LogicOperator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/17
 */
public class LogicalCondition implements Condition{

    private LogicOperator operator;

    private List<Condition> conditions;

    public LogicalCondition() {}

    public LogicalCondition(LogicOperator operator, List<Condition> conditions) {
        // validate operator and conditions
        Objects.requireNonNull(operator, "operator must not be null");
        if (conditions == null || conditions.isEmpty()) {
            throw new IllegalArgumentException("conditions must not be null or empty");
        }
        this.operator = operator;
        this.conditions = conditions;
    }

    @Override
    public boolean evaluate(RuleContext ruleContext) {
        return operator.apply(conditions.stream().map(c -> c.evaluate(ruleContext)).collect(Collectors.toList()));
    }

    @Override
    public Collection<String> involvingParams() {
        Set<String> set = new HashSet<>();
        for (Condition condition: conditions) {
            set.addAll(condition.involvingParams());
        }
        return Collections.unmodifiableSet(set);
    }

    public LogicOperator getOperator() {
        return operator;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setOperator(LogicOperator operator) {
        this.operator = operator;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return "LogicalCondition{" +
                "operator=" + operator +
                ", conditions=" + conditions +
                '}';
    }
}
