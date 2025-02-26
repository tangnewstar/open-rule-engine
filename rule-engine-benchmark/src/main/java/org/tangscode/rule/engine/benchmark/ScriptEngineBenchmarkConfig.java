package org.tangscode.rule.engine.benchmark;

import org.openjdk.jmh.annotations.*;
import org.tangscode.rule.engine.script.config.ScriptConfig;
import org.tangscode.rule.engine.script.wrapper.ScriptEngineWrapper;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
@State(Scope.Benchmark)
public class ScriptEngineBenchmarkConfig {

    // 参数化测试维度
    @Param({"groovy","jexl","javascript"})
    public String language;
    @Param({"100","1000"})
    public int loopSize;

    @Param({"true", "false"})
    private boolean enableCache;

    public ScriptEngineWrapper engine;
    public ScriptConfig config;

    @Setup(Level.Trial)
    public void init() {
        engine = new ScriptEngineWrapper();
        config = new ScriptConfig();
        config.setCacheScript(enableCache);
    }
}
