package org.tangscode.rule.engine.benchmark;

import com.google.common.collect.ImmutableMap;
import org.openjdk.jmh.annotations.*;
import org.tangscode.rule.engine.core.RuleContext;
import org.tangscode.rule.engine.core.ValueRule;
import org.tangscode.rule.engine.core.condition.Conditions;
import org.tangscode.rule.engine.core.enums.ComparisonOperator;

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
public class RuleEngineBenchmark {

    @Benchmark
    public void testValueRule(RuleEngineBenchmarkConfig config) {

        ValueRule valueRule = ValueRule.builder()
                .id("price_tier")
                .when(Conditions.field("amount", ComparisonOperator.GT, 100))
                .then("A")
                .when(Conditions.or(
                        Conditions.field("Level", ComparisonOperator.EQ, "VIP"),
                        Conditions.field("amount", ComparisonOperator.GT, 50)))
                .then("B")
                .defaultValue("C")
                .build();

        RuleContext context = new RuleContext();
        for (int i=0; i < config.loopSize; i++) {
            int random = ThreadLocalRandom.current().nextInt(1,201);
            context.putParam("amount", 101);
            context.putParam("Level", random > 100 ? "Normal" : "VIP");
            valueRule.evaluate(context);
//            System.out.println("amount: " + context.getParam("amount") + " price_tier:" + valueRule.evaluate(context));
        }
    }
}

