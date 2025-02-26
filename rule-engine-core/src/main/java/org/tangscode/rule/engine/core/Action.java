package org.tangscode.rule.engine.core;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/19
 */
public interface Action<T> {
    T execute(RuleContext context);
}
