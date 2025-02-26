package org.tangscode.rule.engine.benchmark;

import com.google.common.collect.ImmutableMap;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
@BenchmarkMode(Mode.Throughput) // 测试吞吐量
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 1) // 3次预热
@Measurement(iterations = 5, time = 1) // 5次正式测量
public class ScriptEngineFixedParamBenchmark {

    @Benchmark
    public void testScriptFixedParam(EngineBenchmarkConfig config) {
        String script = generateRuleScript(config.language);
        for (int i=0; i<config.loopSize; i++) {
            HashMap context = new HashMap();
            context.put("amount", 90);
            context.put("Level", "VIP");
            config.engine.execute(config.language, script, ImmutableMap.of("ctx", context), config.config);
        }
    }

    private String generateRuleScript(String language) {
        switch (language) {
            case "groovy": return "if (ctx.amount > 100) {\n" +
                    "            return 'A'\n" +
                    "        } else if (ctx.Level == 'VIP' || ctx.amount > 50) {\n" +
                    "            return 'B_' + ctx.Level  // 使用Groovy字符串插值\n" +
                    "        } else {\n" +
                    "            return 'C'\n" +
                    "        }";
            case "jexl": return "ctx.amount > 100 ? 'A' \n" +
                    "    : ( (ctx.Level == 'VIP' || ctx.amount > 50) \n" +
                    "        ? 'B_' + ctx.Level  // JEXL中访问上下文对象\n" +
                    "        : 'C' )\n";
            case"javascript": return " ctx.amount > 100 ? 'A' : "+
                    "(ctx.Level === 'VIP' || ctx.amount > 50 ? "+
                    "'B_' + ctx.Level : 'C')";
            default: throw new IllegalArgumentException();
        }
    }
//    private String generateTestScript(String language) {
//        switch(language) {
//            case "groovy": return "def sum=0; 100.times{ sum += it }; sum";
//            default: throw new IllegalArgumentException();
//        }
//    }
}

