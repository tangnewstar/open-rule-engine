package org.tangscode.rule.engine.core.expression;

import org.tangscode.rule.engine.core.RuleContext;
import org.tangscode.rule.engine.script.utils.ScriptUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/20
 */
public class ScriptExpression implements Expression {

    private String language; //脚本语言
    private String script; //脚本内容

    public ScriptExpression(){}

    public ScriptExpression(String language, String script) {
        this.language = language;
        this.script = script;
    }

    public String getLanguage() {
        return language;
    }

    public String getScript() {
        return script;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public Object evaluate(RuleContext context) {
        // 实现脚本引擎调用逻辑
        Map<String, Object> bindings = new HashMap<>();
        bindings.put("$ctx", context);
        return ScriptUtils.eval(language, script, bindings);
//        throw new RuntimeException("not implement ScriptExpression");
    }

}
