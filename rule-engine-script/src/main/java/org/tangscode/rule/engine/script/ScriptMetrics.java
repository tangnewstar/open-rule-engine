package org.tangscode.rule.engine.script;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
// 性能监控接口
public interface ScriptMetrics {
    default void logExecution(String language, String script, long duration) {}
    default void logError(String language, String script, Throwable e) {}
}
