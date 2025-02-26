package org.tangscode.rule.engine.script;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
// 执行结果封装
public class ScriptResult<T> {
    private T result;
    private Throwable error;
    private long executionTime;

    // 构造器、getter/setter

    public ScriptResult() {
    }

    public T getResult() {
        return result;
    }

    public Throwable getError() {
        return error;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
