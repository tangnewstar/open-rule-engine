package org.tangscode.rule.engine.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tangscode.rule.engine.core.condition.Condition;
import org.tangscode.rule.engine.core.condition.FieldCondition;
import org.tangscode.rule.engine.core.enums.ComparisonOperator;
import org.tangscode.rule.engine.core.utils.ResourceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/17
 */
public class ConditionTest {

    public static void main(String[] args) {
        ConditionTest ruleTest = new ConditionTest();
        ruleTest.testRuleCondition();
//        ruleTest.testRuleConditionFromJson();
    }

    public void testRuleCondition() {
        RuleContext ruleContext = new RuleContext();
        ruleContext.putParam("age", 18);
        FieldCondition fieldCondition = new FieldCondition("age", ComparisonOperator.GT, 20);
        long start = System.currentTimeMillis();
        System.out.println(fieldCondition.evaluate(ruleContext));
        System.out.println("testRuleCondition elapsed: " + (System.currentTimeMillis() - start));
    }

    public void testRuleConditionFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        // 建议配置忽略未知属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // read json from file /samples/condition.json
        String json = null;
        try {
            json = ResourceUtil.readResourceFile("samples/condition.json");
        } catch (IOException e) {
            throw new RuntimeException("read file exception", e);
        }

        Condition condition = null;
        try {
            condition = mapper.readValue(json, Condition.class);
        } catch (IOException e) {
            throw new RuntimeException("parse json exception", e);
        }
        System.out.println(condition.toString());

        if (condition != null) {
            double totalElapsed = 0;
            for (int i = 0; i < 10000; i++) {
                HashMap params = new HashMap();
                params.put("income", 50000 + i % 2);
                params.put("vipLevel", 2 + i % 1);
                params.put("creditScore", 700 + i % 10);
                double elapsed = evaluateCondition(condition, params);
                System.out.println("condition elapsed: " + elapsed);
                totalElapsed += elapsed;
            }
            System.out.println("average elapsed: " + totalElapsed / 10000);
        }
    }

    private double evaluateCondition(Condition condition, Map<String, Object> params) {
        long start = System.nanoTime();
        RuleContext ruleContext = new RuleContext();
        params.forEach(ruleContext::putParam);
        System.out.println(condition.evaluate(ruleContext));
        return (System.nanoTime() - start) / 1000000.0;
    }
}
