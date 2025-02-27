package org.tangscode.rule.engine.core.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.codehaus.janino.CompilerFactory;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;
import org.tangscode.rule.engine.core.RuleContext;
import org.tangscode.rule.engine.core.exception.RuleEvaluationException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/27
 */
public class JaninoExpression implements Expression {
    private String expression;
    private ExpressionEvaluator compiledExpression;

    public JaninoExpression() {
    }

    public JaninoExpression(String expression) {
        this.expression = expression;
        compileExpression();
    }

    @Override
    public Object evaluate(RuleContext context) {
        try {
            // 1. 添加防御性拷贝（防止原始Map被外部修改）
            Map<String, Object> safeParams = new HashMap<>(context.getParams()); // 自动解嵌套JavaBean

            // 2. 参数传递方式修正（必须用Object数组包裹）
            if (compiledExpression == null) {
                compileExpression();
            }

            // 3. 新版Janino强制要求数组传参
            return compiledExpression.evaluate(new Object[]{safeParams}); // ✅ 正确传参方式
        } catch (Exception e) {
            throw new RuleEvaluationException("JaninoExpression["+expression+"]执行失败，参数："+context.getParams(), e);
        }
    }

    private void compileExpression() {
        try {
            // 使用Janino编译器解析表达式
            ExpressionEvaluator se = new ExpressionEvaluator();
            se.setParameters(new String[]{"params"}, new Class[]{Map.class}); // 接受参数Map
            se.cook(expression);
            compiledExpression = se;
        } catch (Exception e) {
            throw new RuleEvaluationException("JaninoExpression编译失败: " + expression, e);
        }
    }

//    // 支持嵌套属性访问的辅助方法（示例）
//    private Object getNestedValue(Map<String, Object> params, String key) {
//        String[] parts = key.split("\\.");
//        Object current = params;
//        for (String part : parts) {
//            if (current instanceof Map) {
//                current = ((Map<?, ?>) current).get(part);
//            } else {
//                try {
//                    current = PropertyUtils.getProperty(current, part);
//                } catch (Exception e) {
//                    throw new RuleEvaluationException("属性访问失败: " + key, e);
//                }
//            }
//        }
//        return current;
//    }

    // Jackson序列化需要的getter/setter
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

