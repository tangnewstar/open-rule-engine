package org.tangscode.rule.engine.script.adapter;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/24
 */

import javax.script.ScriptException;
import java.util.Map;
import java.util.function.Function;

/**
 * 脚本引擎核心抽象层（兼容JSR-223并扩展）
 */
public interface ScriptEngineAdapter {
    // 合并上下文管理和执行（推荐用法）
    Object eval(String script, Map<String, Object> bindings) throws ScriptException;

    // 支持函数扩展的流畅接口设计
    void registerFunc(String name, Function<Map<String, Object>, Object> function);

}


//public interface ScriptEngineAdapter {
//    // 核心能力
//    Object eval(String script, Map<String, Object> bindings) throws ScriptException;
//    CompiledScript compile(String script) throws ScriptException;
//
//    // 扩展功能
//    Object evalExpression(String expr, Map<String, Object> bindings);
//    void addFunction(String name, Function<Map<String, Object>, Object> function);
//
//    // 性能增强
//    void warmUp(int iterations); // JIT预热
//    void resetCache();
//}

