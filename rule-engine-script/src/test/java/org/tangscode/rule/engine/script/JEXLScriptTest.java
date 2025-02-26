package org.tangscode.rule.engine.script;

import org.apache.commons.jexl3.*;

import java.util.Arrays;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
public class JEXLScriptTest {

    public static void main(String[] args) {
        JEXLScriptTest test = new JEXLScriptTest();
        test.testJexlForLoop();
    }

    public void testJexlForLoop() {
        JexlEngine jexl = new JexlBuilder()
                .debug(true)
                .silent(false)
                .create();

        // 注意：语句块内分号必须严格遵循规则
        String script = "for (item: list) {total += item}";
        MapContext mapContext = new MapContext();
        mapContext.set("list", Arrays.asList(1,2,3,4,5));
        mapContext.set("total", 0);;
        JexlExpression expr = jexl.createExpression(script);
        Object result = expr.evaluate(mapContext);
        System.out.println(mapContext.get("total"));
        assert result.equals(4950);
    }




}
