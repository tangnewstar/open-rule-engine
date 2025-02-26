package org.tangscode.rule.engine.core.exception;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/21
 */
public class RuleEvaluationException extends RuntimeException {
    public RuleEvaluationException(String message) {
        super(message);
    }

    public RuleEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
