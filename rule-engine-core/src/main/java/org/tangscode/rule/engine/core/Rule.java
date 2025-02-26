package org.tangscode.rule.engine.core;

import org.tangscode.rule.engine.core.exception.RuleEvaluationException;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/17
 */
public interface Rule {
    String id();
    int priority();
    Object evaluate(RuleContext context);
}


