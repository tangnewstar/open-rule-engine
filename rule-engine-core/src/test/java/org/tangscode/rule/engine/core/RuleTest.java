package org.tangscode.rule.engine.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tangscode.rule.engine.core.condition.Conditions;
import org.tangscode.rule.engine.core.enums.ComparisonOperator;
import org.tangscode.rule.engine.core.expression.JaninoExpression;
import org.tangscode.rule.engine.core.utils.ResourceUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/20
 */
public class RuleTest {
    private static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    public static void main(String[] args) {
        RuleTest ruleTest = new RuleTest();
//        ruleTest.testBranchRuleFluentAPI();
//        ruleTest.testMultiBranchRule();
//        ruleTest.testExpressionRule();
//        ruleTest.testJaninoExpressionRule();
        ruleTest.testJaninoExpressionRuleAPI();
    }

    public void testBranchRuleFluentAPI() {
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
        context.putParam("amount", 101);
        System.out.println("amount: " + context.getParam("amount") + " price_tier:" + branchRule.evaluate(context));
        context.putParam("amount", 100);
        context.putParam("Level", "Normal");
        System.out.println("amount: " + context.getParam("amount") + " price_tier:" + branchRule.evaluate(context));
        context.putParam("amount", 50);
        System.out.println("amount: " + context.getParam("amount") + " price_tier:" + branchRule.evaluate(context));
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

        SerializableRule rule = null;
        try {
            rule = objectMapper.readValue(json, SerializableRule.class);
        } catch (IOException e) {
            log.error("testMultiBranchRule Deserialize exception", e);
        }

        RuleContext ctx1 = new RuleContext();
        ctx1.putParam("amount", 10);
        ctx1.putParam("Level", "VIP");
        System.out.println(rule.evaluate(ctx1));
        ctx1.putParam("Level", "Normal");
        System.out.println(rule.evaluate(ctx1));
    }

    void testExpressionRule() {
        String json;
        try {
            json = ResourceUtil.readResourceFile("samples/expression_rule.json");
        } catch (IOException e) {
            throw new RuntimeException("read file exception", e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        // 建议配置忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SerializableRule rule = null;
        try {
            rule = objectMapper.readValue(json, SerializableRule.class);
        } catch (IOException e) {
            log.error("testMultiBranchRule Deserialize exception", e);
        }
        System.out.println(rule.evaluate(null));
    }

    void testJaninoExpressionRule() {
        String json;
        try {
            json = ResourceUtil.readResourceFile("samples/janino_expression_rule.json");
        } catch (IOException e) {
            throw new RuntimeException("read file exception", e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        // 建议配置忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SerializableRule rule = null;
        try {
            rule = objectMapper.readValue(json, SerializableRule.class);
        } catch (IOException e) {
            log.error("testMultiBranchRule Deserialize exception", e);
        }
        RuleContext context = new RuleContext();
        context.putParam("age", 20);
        System.out.println(rule.evaluate(context));
    }

    void testJaninoExpressionRuleAPI() {
        ExpressionRule expressionRule = ExpressionRule.create("price_tier", new JaninoExpression("(Integer)params.get(\"amount\") > 100 ? \"A\" : ((Integer)params.get(\"amount\") > 50 || params.get(\"Level\") == \"VIP\" ? \"B\" : \"C\")"));
        RuleContext ruleContext = new RuleContext();
        ruleContext.putParam("amount", 50);
        ruleContext.putParam("Level", "VIP");
        Object result = expressionRule.evaluate(ruleContext);
        System.out.println("expr: " + expressionRule + " result: " + result);
    }
}
