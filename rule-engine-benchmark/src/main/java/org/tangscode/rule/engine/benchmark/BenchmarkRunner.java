package org.tangscode.rule.engine.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
public class BenchmarkRunner {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
//                .include(ScriptEngineBenchmark.class.getSimpleName())
//                .include(ScriptEngineFixedParamBenchmark.class.getSimpleName())
                .include(RuleEngineBenchmark.class.getSimpleName())
                .forks(1) // 使用独立JVM进程
                .build();

        new Runner(opt).run();
    }
}

