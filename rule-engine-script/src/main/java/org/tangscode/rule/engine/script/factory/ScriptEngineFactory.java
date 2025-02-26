//package org.tangscode.rule.engine.script.factory;
//
//import com.google.common.collect.ImmutableMap;
//import org.tangscode.rule.engine.script.adapter.ScriptEngineAdapter;
//import org.tangscode.rule.engine.script.enums.LangType;
//
//import java.util.Map;
//import java.util.function.Supplier;
//
///**
// * @author tangxinxing
// * @version 1.0
// * @description
// * @date 2025/2/24
// */
//public class ScriptEngineFactory {
//    private static final Map<LangType, Supplier<ScriptEngineAdapter>> ENGINES = ImmutableMap.of(
//            LangType.GROOVY, new ,
//            LangType.LUAJ, LuajEngineAdapter::new,
//            LangType.JEXL, JexlEngineAdapter::new
//    );
//
//    public static ScriptEngineAdapter create(LangType type, Config config) {
//        return ENGINES.get(type).get().init(config);
//    }
//}
//
//
