package org.tangscode.rule.engine.core;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;
import org.tangscode.rule.engine.core.expression.Expression;
import org.tangscode.rule.engine.core.expression.JaninoExpression;
import org.tangscode.rule.engine.core.expression.JaninoScriptExpression;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/27
 */
public class ExpressionTest {

    public static void main(String[] args) {
        ExpressionTest test = new ExpressionTest();
//        test.testJaninoExpression();
//        test.testJanino();
//        test.testJaninoMap();
        test.testJaninoScriptExpression();
    }

    void testJaninoExpression() {
        // 创建规则
        Expression expr = new JaninoExpression("((Integer) params.get(\"age\")) > 18 ? \"adult\" : \"child\"");

//// 或更安全的写法（处理可能的null值）：
//        (params.age != null ? (Integer) params.age : 0) > 18 ? "adult" : "child"

        // 执行计算
        RuleContext context = new RuleContext();
        context.putParam("age", 20);
        Object result = expr.evaluate(context); // 返回"adult"
        System.out.println(result);
    }

    void testJaninoScriptExpression() {
        // 创建规则
        Expression expr = new JaninoScriptExpression("return ((Integer) params.get(\"age\")) > 18 ? \"adult\" : \"child\";");

//// 或更安全的写法（处理可能的null值）：
//        (params.age != null ? (Integer) params.age : 0) > 18 ? "adult" : "child"

        // 执行计算
        RuleContext context = new RuleContext();
        context.putParam("age", 20);
        Object result = expr.evaluate(context); // 返回"adult"
        System.out.println(result);
    }

    void testJanino() {
        try {
            String exp = "2*(a+b)";
            ScriptEvaluator evaluator = new ExpressionEvaluator();
            evaluator.setParameters(new String[] {"a", "b"}, new Class[] {int.class, int.class});
            evaluator.cook(exp);
            Object res = evaluator.evaluate(new Object[]{19, 23});
            System.out.println(exp + "=" + res);
        } catch (CompileException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    void testJaninoMap() {
        // 示例脚本，使用 Map 参数
        String script =
                "return map.get(\"greeting\") + \", \" + map.get(\"name\") + \"!\";";

        try {
            // 创建 ScriptEvaluator 实例，指定返回类型、参数名和参数类型
            ScriptEvaluator se = new ScriptEvaluator(
                    script,                      // 脚本代码
                    Object.class,                // 返回值类型
                    new String[]{"map"},         // 参数名列表
                    new Class<?>[]{Map.class}    // 参数类型列表
            );

            // 准备传入的 Map 参数
            Map<String, Object> params = new HashMap<>();
            params.put("greeting", "Hello");
            params.put("name", "Janino");

            // 执行脚本，传入 Map 参数
            Object result = se.evaluate(new Object[]{params});

            // 输出结果
            System.out.println(result);
        } catch (CompileException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
