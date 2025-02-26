package org.tangscode.rule.engine.core;

import org.tangscode.rule.engine.core.exception.ParamNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/17
 */
public class RuleContext {

    private Map<String, Object> params;
    private Map<String, Object> result;

    public RuleContext() {
        this.params = new ConcurrentHashMap<>();
        this.result = new ConcurrentHashMap<>();
    }

    public Object getParam(String key) {
        validateParamKey(key);
        return params.get(key);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Object getParamThrowsIfNull(String key) throws ParamNotFoundException {
        if (!params.containsKey(key)) {
            throw new ParamNotFoundException("RuleContext param:[" + key + "] not found");
        }
        return getParam(key);
    }

    public void putParam(String key, Object value) {
        validateParamKey(key);
        params.put(key, value);
    }

    public void putResult(String key, Object value) {
        this.result.put(key, value);
    }

    private void validateParamKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("RuleContext Param key null");
        }
    }
}
