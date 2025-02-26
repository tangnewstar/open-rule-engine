//package org.tangscode.rule.engine.script.adapter;
//
///**
// * @author tangxinxing
// * @version 1.0
// * @description
// * @date 2025/2/24
// */
//
//import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
//
//import javax.script.*;
//import java.util.Collections;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.function.Function;
//
////// 内存敏感场景的配置
////new Config()
////    .setMaxCacheSize(1000) // 防止OOM
////    .setExpressionTTL(Duration.ofMinutes(30))
////        .setScriptGCThreshold(500_000); // LuaJ专用配置
//
////// 使用JEXL处理高频计算
////String formula = "(Credit * 0.6) + (txnAmount * 0.3) - riskScore";
////Map<String, Object> params = Map.of(
////        "Credit", 780,
////        "txnAmount", 15000,
////        "riskScore", 45
////);
////
////double result = (Double) jexlEngine.evalExpression(formula, params);
//
//
///**
// * 风控专用Groovy脚本引擎适配器（线程安全）
// * 特性：
// * 1. 基于SHA-256的脚本缓存（防止内存泄漏）
// * 2. 预编译加速（缓存命中率>99%时执行耗时<1ms）
// * 3. 安全沙箱（限制危险API调用）
// */
//public class GroovyEngineAdapter implements ScriptEngineAdapter {
//
//    private static final int MAX_CACHE_SIZE = 1024;
//    private final ScriptEngine engine;
//    private final Compilable compilable;
//    private final Invocable invocable;
//
//    // 使用LRU缓存策略（线程安全）
//    private final Map<String, CompiledScript> scriptCache =
//            Collections.synchronizedMap(new LinkedHashMap<String, CompiledScript>(16, 0.75f, true) {
//                @Override
//                protected boolean removeEldestEntry(Map.Entry eldest) {
//                    return size() > MAX_CACHE_SIZE;
//                }
//            });
//
//    // 沙箱配置（风控场景关键安全限制）
//    private static final String[] BLACKLIST_CLASSES = {
//            "java.lang.Runtime",
//            "java.lang.ProcessBuilder",
//            "groovy.lang.GroovyShell"
//    };
//
//    public GroovyEngineAdapter() {
//        this.engine = new GroovyScriptEngineFactory().getScriptEngine();
//        this.compilable = (Compilable) engine;
//        this.invocable = (Invocable) engine;
//
//        // 初始化安全过滤器
//        engine.getContext().setAttribute("#jsr223.groovy.engine.secure",
//                new SecurityManager() {
//                    @Override
//                    public void checkPackageAccess(String pkg) {
//                        if (pkg.startsWith("java.lang.reflect")) {
//                            throw new SecurityException("Reflection operations prohibited");
//                        }
//                    }
//                }, ScriptContext.ENGINE_SCOPE);
//    }
//
//    @Override
//    public Object eval(String script, Map<String, Object> bindings) throws ScriptException {
//        checkScriptSafety(script); // 安全校验
//
//        CompiledScript compiled = scriptCache.computeIfAbsent(generateKey(script), k -> {
//            try {
//                return compilable.compile(script);
//            } catch (ScriptException e) {
//                throw new RuntimeException("Compilation failed: " + e.getMessage());
//            }
//        });
//
//        Bindings engineBindings = engine.createBindings();
//        engineBindings.putAll(bindings);
//        return compiled.eval(engineBindings);
//    }
//
//    @Override
//    public void registerFunc(String name, Function<Map<String, Object>, Object> function) {
//
//    }
//
//    // 生成基于内容哈希的缓存键（避免相同脚本不同字符串实例导致的重复编译）
//    private String generateKey(String script) {
//        return DigestUtils.sha256Hex(script);
//    }
//
//    // 风控场景安全增强：脚本预处理
//    private String addSafetyWrapper(String script) {
//        String src = "import static com.risk.control.ScriptValidator.*;" +
//            " validateInput($args); " + // 输入参数校验
//            "try { " +
//            "    ${script} " +
//            "} finally { " +
//            "   cleanThreadLocals(); " + // 清理线程残留数据
//            "}";
//        return src.replace("${script}", script);
//    }
//
//    // 黑名单类/方法检测
//    private void checkScriptSafety(String script) {
//        for (String cls : BLACKLIST_CLASSES) {
//            if (script.contains(cls)) {
//                throw new SecurityException("Forbidden class reference: " + cls);
//            }
//        }
//
//        if (script.matches("(?s).*getMetaClass\\(\\).*invokeMethod\\(.*")) {
//            throw new SecurityException("Dynamic method invocation prohibited");
//        }
//    }
//}
