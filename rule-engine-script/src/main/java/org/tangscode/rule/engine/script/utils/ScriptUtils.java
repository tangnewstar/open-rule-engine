package org.tangscode.rule.engine.script.utils;
import javax.script.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/24
 */


/**
 * 脚本执行工具类（线程安全）
 */
public class ScriptUtils {
    private static final ScriptEngineManager ENGINE_MANAGER = new ScriptEngineManager();

    /**
     * 执行脚本并返回结果（无变量绑定）
     */
    public static Object eval(String language, String script) {
        return eval(language, script, Collections.emptyMap());
    }

    /**
     * 执行脚本并返回结果（带变量绑定）
     */
    public static Object eval(String language, String script, Map<String, Object> bindings) {
        ScriptEngine engine = ENGINE_MANAGER.getEngineByName(language);
        if (engine == null) {
            throw new ScriptException("Unsupported script language: " + language);
        }

        try {
            Bindings engineBindings = engine.createBindings();
            engineBindings.putAll(bindings);
            return engine.eval(script, engineBindings);
        } catch (ScriptException | javax.script.ScriptException e) {
            throw new ScriptExecutionException("Script execution failed: " + e.getMessage(), e);
        }
    }

    /**
     * 执行脚本文件
     */
    public static Object evalFile(String language, String filePath) throws IOException {
        return evalFile(language, filePath, Collections.emptyMap());
    }

    /**
     * 执行脚本文件（带变量绑定）
     */
    public static Object evalFile(String language, String filePath, Map<String, Object> bindings) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        String script = new String(bytes, Charset.defaultCharset());
        return eval(language, script, bindings);
    }

    /**
     * 创建指定语言的脚本上下文
     */
    public static ScriptContext createContext(String language) {
        ScriptEngine engine = ENGINE_MANAGER.getEngineByName(language);
        return engine.getContext();
    }

    // 自定义异常类
    public static class ScriptException extends RuntimeException {
        public ScriptException(String message) {
            super(message);
        }
    }

    public static class ScriptExecutionException extends ScriptException {
        public ScriptExecutionException(String message, Throwable cause) {
            super(message + ": " + cause.getMessage());
            initCause(cause);
        }
    }
}

