package org.tangscode.rule.engine.benchmark;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/26
 */
@State(Scope.Benchmark)
public class RuleEngineBenchmarkConfig {
    @Param({"100","1000"})
    public int loopSize;
}
