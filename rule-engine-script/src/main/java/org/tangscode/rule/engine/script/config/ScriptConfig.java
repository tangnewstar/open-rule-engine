package org.tangscode.rule.engine.script.config;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/25
 */
public class ScriptConfig {
    private boolean cacheScript;  // 是否缓存脚本
    private long cacheSize = 100; // 缓存最大容量

    // 构造器、getter/setter

    /**
     * 默认启用缓存
     */
    public ScriptConfig() {
        this(true);
    }

    public ScriptConfig(boolean cacheScript) {
        this.cacheScript = cacheScript;
    }

    public boolean isCacheScript() {
        return cacheScript;
    }

    public long getCacheSize() {
        return cacheSize;
    }

    public void setCacheScript(boolean cacheScript) {
        this.cacheScript = cacheScript;
    }

    public void setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
    }
}
