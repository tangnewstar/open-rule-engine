package org.tangscode.rule.engine.script.exception;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
public class ScriptCompilationException extends RuntimeException {
    public ScriptCompilationException(String message) {
        super(message);
    }

    public ScriptCompilationException(String message, Throwable cause) {
        super(message, cause);
    }
}
