package org.tangscode.rule.engine.benchmark;

import org.openjdk.jmh.annotations.*;
import org.tangscode.rule.engine.core.BranchRule;
import org.tangscode.rule.engine.core.ExpressionRule;
import org.tangscode.rule.engine.core.RuleContext;
import org.tangscode.rule.engine.core.condition.Conditions;
import org.tangscode.rule.engine.core.enums.ComparisonOperator;
import org.tangscode.rule.engine.core.expression.Expression;
import org.tangscode.rule.engine.core.expression.JaninoExpression;
import org.tangscode.rule.engine.core.expression.JaninoScriptExpression;

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

//    @Benchmark
    public void testBranchRule(RuleEngineBenchmarkConfig config) {

        BranchRule branchRule = BranchRule.builder()
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
            branchRule.evaluate(context);
        }
    }

//    @Benchmark
    public void testJaninoExpressionRule(RuleEngineBenchmarkConfig config) {
        ExpressionRule expressionRule = ExpressionRule.create("price_tier", new JaninoExpression("(Integer)params.get(\"amount\") > 100 ? \"A\" : ((Integer)params.get(\"amount\") > 50 || params.get(\"Level\") == \"VIP\" ? \"B\" : \"C\")"));
        RuleContext ruleContext = new RuleContext();
        for (int i=0; i < config.loopSize; i++) {
            int random = ThreadLocalRandom.current().nextInt(1,201);
            ruleContext.putParam("amount", 101);
            ruleContext.putParam("Level", random > 100 ? "Normal" : "VIP");
            expressionRule.evaluate(ruleContext);
        }
    }

    @Benchmark
    public void testJaninoScriptExpression(RuleEngineBenchmarkConfig config) {
        // 创建规则
        Expression expr = new JaninoScriptExpression("return (Integer)params.get(\"amount\") > 100 ? \"A\" : ((Integer)params.get(\"amount\") > 50 || params.get(\"Level\") == \"VIP\" ? \"B\" : \"C\");");
        // 执行计算
        RuleContext context = new RuleContext();
        for (int i=0; i < config.loopSize; i++) {
            int random = ThreadLocalRandom.current().nextInt(1,201);
            context.putParam("amount", 101);
            context.putParam("Level", random > 100 ? "Normal" : "VIP");
            expr.evaluate(context);
        }
    }
}

