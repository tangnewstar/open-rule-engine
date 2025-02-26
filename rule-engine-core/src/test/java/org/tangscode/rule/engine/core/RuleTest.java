package org.tangscode.rule.engine.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tangscode.rule.engine.core.condition.Conditions;
import org.tangscode.rule.engine.core.enums.ComparisonOperator;
import org.tangscode.rule.engine.core.utils.ResourceUtil;

import java.io.IOException;
import java.util.Set;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/20
 */
public class RuleTest {
    private static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    public static void main(String[] aegs) {
        RuleTest ruleTest = new RuleTest();
//        ruleTest.testSimpleRule();
        ruleTest.testBranchRuleFluentAPI();
        ruleTest.testMultiBranchRule();
    }

    public void testSimpleRule() {
        SimpleRule<Object> simpleRule = SimpleRule.define("age_rule")
                .defaultValue("未知")
                .when(ctx -> (Integer)ctx.getParam("age") < 18)
                .then(ctx -> "未成年").build();

        RuleContext ruleContext = new RuleContext();
        ruleContext.putParam("age", 18);
        System.out.println("age: " + ruleContext.getParam("age") + " result: " + simpleRule.evaluate(ruleContext));
        ruleContext.putParam("age", 10);
        System.out.println("age: " + ruleContext.getParam("age") + " result: " + simpleRule.evaluate(ruleContext));
    }

    public void testFluentAPIRule() {
    }

    public void testBranchRuleFluentAPI() {
//        ValueRule valueRule = ValueRule.builder()
//                .id("price_tier")
//                .branch(ctx -> (Integer)ctx.getParam("amount") > 100, ctx -> "A")
//                .branch(ctx -> (Integer)ctx.getParam("amount") > 50, ctx -> "B")
//                .defaultValue("C")
//                .build();
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
        context.putParam("amount", 101);
        System.out.println("amount: " + context.getParam("amount") + " price_tier:" + valueRule.evaluate(context));
        context.putParam("amount", 100);
        context.putParam("Level", "Normal");
        System.out.println("amount: " + context.getParam("amount") + " price_tier:" + valueRule.evaluate(context));
        context.putParam("amount", 50);
        System.out.println("amount: " + context.getParam("amount") + " price_tier:" + valueRule.evaluate(context));
    }

    // 新版多分支规则测试
    void testMultiBranchRule() {
        String json;
        try {
            json = ResourceUtil.readResourceFile("samples/branch_rule.json");
        } catch (IOException e) {
            throw new RuntimeException("read file exception", e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        // 建议配置忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ValueRule rule = null;
        try {
            rule = objectMapper.readValue(json, ValueRule.class);
        } catch (IOException e) {
            log.error("testMultiBranchRule Deserialize exception", e);
        }

        RuleContext ctx1 = new RuleContext();
        ctx1.putParam("amount", 10);
        ctx1.putParam("Level", "VIP");
        System.out.println(rule.evaluate(ctx1));
    }

}
