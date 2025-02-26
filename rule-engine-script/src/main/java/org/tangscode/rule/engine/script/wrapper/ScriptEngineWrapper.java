package org.tangscode.rule.engine.script.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tangscode.rule.engine.script.ScriptResult;
import org.tangscode.rule.engine.script.config.ScriptConfig;
import org.tangscode.rule.engine.script.exception.ScriptCompilationException;

import javax.script.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
// 核心引擎封装
public class ScriptEngineWrapper {

    private static final Logger log = LoggerFactory.getLogger(ScriptEngineWrapper.class);

    private final ScriptEngineManager manager = new ScriptEngineManager();
    private final Map<String, ScriptEngine> engines = new ConcurrentHashMap<>();
    private final Map<String, CompiledScript> scriptCache = new ConcurrentHashMap<>();

    public ScriptResult<Object> execute(String language, String script, Map<String, Object> params, ScriptConfig config) {
        long start = System.currentTimeMillis();
        ScriptResult<Object> result = new ScriptResult<>();
        try {
            ScriptEngine engine = engines.computeIfAbsent(language, k -> manager.getEngineByName(k));
            Bindings bindings = prepareBindings(engine, params);

            CompiledScript compiled = resolveCompiledScript(engine, language, script, config);
            result.setResult(compiled != null ?
                    compiled.eval(bindings) :
                    engine.eval(script, bindings));

        } catch (ScriptException | ClassCastException | ScriptCompilationException e) {
            result.setError(e);
        } finally {
            result.setExecutionTime(System.currentTimeMillis() - start);
        }
        return result;
    }

    private Bindings prepareBindings(ScriptEngine engine, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        Bindings bindings = engine.createBindings();
        bindings.putAll(params);
        return bindings;
    }

    // 启用缓存的脚本，会先编译再执行
    private CompiledScript resolveCompiledScript(ScriptEngine engine, String language,
                                                 String script, ScriptConfig config) {
        if (!config.isCacheScript()) {
            return null;
        }
        if (config.isCacheScript() && !(engine instanceof  Compilable)) {
            log.warn("Script language: {} doesn't support compilation", language);
            return null;
        }

        Compilable compilable = (Compilable) engine;
        if (!config.isCacheScript()) {
            return compileWithExceptionHandling(compilable, script, language);
        }

        return scriptCache.computeIfAbsent(
                script.hashCode() + "@" + language,
                k -> compileWithExceptionHandling(compilable, script, language)
        );
    }

    private CompiledScript compileWithExceptionHandling(Compilable compilable,
                                                        String script, String language) {
        try {
            return compilable.compile(script);
        } catch (ScriptException e) {
            throw new ScriptCompilationException("Script compilation failed, language: " + language
                    + " script: " + script, e);
        }
    }

}
