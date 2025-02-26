package org.tangscode.rule.engine.script;

import com.google.common.collect.ImmutableMap;
import org.tangscode.rule.engine.script.config.ScriptConfig;
import org.tangscode.rule.engine.script.wrapper.ScriptEngineWrapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
public class ScriptEngineTest {

    public static void main(String[] args) {
        ScriptEngineWrapper wrapper = new ScriptEngineWrapper();
        ScriptConfig config = new ScriptConfig(false);

        HashMap context = new HashMap();
        context.put("amount", 100);
        context.put("Level", "Normal");

        List<String> languages = Arrays.asList("groovy", "jexl", "javascript");
        for (String lang: languages) {
            // 执行Groovy脚本
            ScriptResult<Object> result = wrapper.execute(lang,
                    generateRuleScript(lang), ImmutableMap.of("ctx", context), config);
            if (result.getError() != null) {
                System.out.println(lang + "执行错误: " + result.getError().getMessage());
            } else {
                System.out.println(lang + "执行结果: " + result.getResult() + " 执行耗时: " + result.getExecutionTime()); // 输出8
            }
        }
    }

    private static String generateRuleScript(String language) {
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
}
