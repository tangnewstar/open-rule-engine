package org.tangscode.rule.engine.core.condition;

import org.tangscode.rule.engine.core.RuleContext;

import java.util.Collection;
import java.util.Collections;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/27
 */
public class TrueCondition implements Condition {
    @Override
    public boolean evaluate(RuleContext ruleContext) {
        return true;
    }

    @Override
    public Collection<String> involvingParams() {
        return Collections.emptyList();
    }
}
