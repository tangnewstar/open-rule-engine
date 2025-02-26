
#### 固定参数执行
| Benchmark                                             | (enableCache) | (language)  | (loopSize) | Mode  | Cnt | Score     | Error     | Units |
|-------------------------------------------------------|---------------|-------------|------------|-------|-----|-----------|-----------|-------|
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | true          | groovy      | 100        | thrpt | 5   | 1346.019  | ± 555.047 | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | true          | groovy      | 1000       | thrpt | 5   | 129.083   | ± 24.492  | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | true          | jexl        | 100        | thrpt | 5   | 2070.666  | ± 433.532 | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | true          | jexl        | 1000       | thrpt | 5   | 210.240   | ± 49.419  | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | true          | javascript  | 100        | thrpt | 5   | 83.418    | ± 54.301  | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | true          | javascript  | 1000       | thrpt | 5   | 8.763     | ± 4.395   | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | false         | groovy      | 100        | thrpt | 5   | 1208.840  | ± 199.432 | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | false         | groovy      | 1000       | thrpt | 5   | 128.559   | ± 78.683  | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | false         | jexl        | 100        | thrpt | 5   | 171.616   | ± 87.585  | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | false         | jexl        | 1000       | thrpt | 5   | 13.409    | ± 9.560   | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | false         | javascript  | 100        | thrpt | 5   | 50.858    | ± 80.107  | ops/s |
| ScriptEngineFixedParamBenchmark.testScriptFixedParam  | false         | javascript  | 1000       | thrpt | 5   | 5.803     | ± 1.898   | ops/s |

### Benchmark Results Analysis

#### 1. Cache Impact

##### 1.1 With Cache Enabled (enableCache=true)

- **Groovy:**
  - loopSize=100: Throughput = 1346.019 ± 555.047 ops/s
  - loopSize=1000: Throughput = 129.083 ± 24.492 ops/s

- **JEXL:**
  - loopSize=100: Throughput = 2070.666 ± 433.532 ops/s
  - loopSize=1000: Throughput = 210.240 ± 49.419 ops/s

- **JavaScript:**
  - loopSize=100: Throughput = 83.418 ± 54.301 ops/s
  - loopSize=1000: Throughput = 8.763 ± 4.395 ops/s

##### 1.2 Without Cache (enableCache=false)

- **Groovy:**
  - loopSize=100: Throughput = 1208.840 ± 199.432 ops/s
  - loopSize=1000: Throughput = 128.559 ± 78.683 ops/s

- **JEXL:**
  - loopSize=100: Throughput = 171.616 ± 87.585 ops/s
  - loopSize=1000: Throughput = 13.409 ± 9.560 ops/s

- **JavaScript:**
  - loopSize=100: Throughput = 50.858 ± 80.107 ops/s
  - loopSize=1000: Throughput = 5.803 ± 1.898 ops/s

#### 2. Comparison and Analysis

##### 2.1 Cache Impact on Performance

- **Groovy:**
  - With cache, better performance at loopSize=100 (1346.019 vs 1208.840), slight decrease at loopSize=1000 (129.083 vs 128.559). Cache significantly improves performance for smaller loop sizes, but the advantage diminishes with larger loop sizes.

- **JEXL:**
  - With cache, significant performance improvement at both loopSize=100 (2070.666 vs 171.616) and loopSize=1000 (210.240 vs 13.409). JEXL shows a substantial efficiency boost with cache, especially for larger loop sizes.

- **JavaScript:**
  - With cache, better performance at loopSize=100 (83.418 vs 50.858), minimal change at loopSize=1000 (8.763 vs 5.803). JavaScript is more affected by cache at smaller loop sizes, with less impact at larger loop sizes.

##### 2.2 Relative Performance of Different Languages

- **JEXL** consistently performs best, especially with cache enabled, maintaining high throughput even with larger loop sizes. Without cache, it still performs well at loopSize=100.
- **Groovy** performs well without cache, particularly at loopSize=100. With cache, the performance improvement is less pronounced compared to JEXL.
- **JavaScript** has the lowest overall performance, especially without cache, regardless of loop size.

#### 3. Conclusion

- **JEXL** is the best-performing language, particularly with cache enabled, maintaining efficient execution even with large loop sizes.
- **Groovy** is a good choice without cache, especially for smaller loop sizes. If caching is not required, Groovy is a reasonable option.
- **JavaScript** has the lowest performance in all scenarios. It is not recommended for performance-sensitive tasks unless there are specific requirements or constraints.

#### 4. Further Optimization Suggestions

- **Evaluate Cache Strategy:** Decide whether to enable cache based on the application scenario. If most operations are repetitive and not sensitive to initialization time, enabling cache will significantly improve performance.
- **Choose the Right Script Engine:** Based on the analysis, JEXL is the optimal choice for performance, especially for handling large-scale data or frequent script calls. If performance is not critical, or there are specific dependencies, Groovy or JavaScript can be considered.
- **Adjust Loop Size:** If possible, reduce unnecessary large loop sizes to alleviate performance burden.

#### 动态传参执行
| Benchmark                                  | (enableCache) | (language)  | (loopSize) | Mode  | Cnt | Score     | Error     | Units |
|--------------------------------------------|---------------|-------------|------------|-------|-----|-----------|-----------|-------|
| ScriptEngineBenchmark.testScriptExecution  | true          | groovy      | 100        | thrpt | 5   | 1077.658  | ± 895.774 | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | true          | groovy      | 1000       | thrpt | 5   | 142.270   | ± 13.181  | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | true          | jexl        | 100        | thrpt | 5   | 2182.655  | ± 533.306 | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | true          | jexl        | 1000       | thrpt | 5   | 226.128   | ± 15.990  | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | true          | javascript  | 100        | thrpt | 5   | 92.915    | ± 55.982  | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | true          | javascript  | 1000       | thrpt | 5   | 9.617     | ± 4.420   | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | false         | groovy      | 100        | thrpt | 5   | 1418.987  | ± 98.489  | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | false         | groovy      | 1000       | thrpt | 5   | 137.825   | ± 9.509   | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | false         | jexl        | 100        | thrpt | 5   | 182.972   | ± 101.007 | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | false         | jexl        | 1000       | thrpt | 5   | 18.830    | ± 4.051   | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | false         | javascript  | 100        | thrpt | 5   | 86.603    | ± 41.087  | ops/s |
| ScriptEngineBenchmark.testScriptExecution  | false         | javascript  | 1000       | thrpt | 5   | 7.741     | ± 2.107   | ops/s |
# 1. 缓存的影响

## 1.1 启用缓存 (enableCache=true)

- **Groovy:**
   - loopSize=100 的吞吐量为 867.169 ± 831.015 ops/s
   - loopSize=1000 的吞吐量为 93.713 ± 17.037 ops/s

- **JEXL:**
   - loopSize=100 的吞吐量为 2102.384 ± 282.779 ops/s
   - loopSize=1000 的吞吐量为 209.043 ± 54.301 ops/s

- **JavaScript:**
   - loopSize=100 的吞吐量为 67.447 ± 7.949 ops/s
   - loopSize=1000 的吞吐量为 5.822 ± 0.922 ops/s

## 1.2 不启用缓存 (enableCache=false)

- **Groovy:**
   - loopSize=100 的吞吐量为 1316.253 ± 141.825 ops/s
   - loopSize=1000 的吞吐量为 129.371 ± 28.554 ops/s

- **JEXL:**
   - loopSize=100 的吞吐量为 25.413 ± 3.251 ops/s
   - loopSize=1000 的吞吐量为 2.408 ± 0.634 ops/s

- **JavaScript:**
   - loopSize=100 的吞吐量为 49.893 ± 20.407 ops/s
   - loopSize=1000 的吞吐量为 5.532 ± 0.303 ops/s

# 2. 对比与分析

## 2.1 缓存对性能的影响

- **Groovy：** 启用缓存时，在 loopSize=100 下表现较好（867.169 vs 1316.253），但在 loopSize=1000 下稍有下降（93.713 vs 129.371）。这表明对于较小的循环次数，缓存可以显著提升性能，但随着循环次数增加，缓存的优势逐渐减弱。

- **JEXL：** 启用缓存时，无论是在 loopSize=100 还是 loopSize=1000 下，性能都有明显提升（2102.384 vs 25.413 和 209.043 vs 2.408）。这说明 JEXL 在启用缓存的情况下，能够大幅度提高执行效率，特别是在较大循环次数下。

- **JavaScript：** 启用缓存时，loopSize=100 下表现较好（67.447 vs 49.893），但在 loopSize=1000 下变化不大（5.822 vs 5.532）。因此，JavaScript 在小循环时受缓存影响较大，而在大循环时影响较小。

## 2.2 不同语言的相对性能

- **JEXL** 在大多数情况下表现出色，特别是在启用缓存时，其吞吐量远高于其他两种语言。即使不启用缓存，它在 loopSize=100 下也保持了较好的性能。

- **Groovy** 在不启用缓存的情况下表现较好，尤其是在 loopSize=100 时。但在启用缓存后，性能提升不如 JEXL 明显。

- **JavaScript** 总体上性能最低，尤其在不启用缓存的情况下，无论是 loopSize=100 还是 loopSize=1000，其吞吐量都相对较低。

# 3. 结论

- **JEXL** 是三种语言中性能最佳的选择，特别是当启用了缓存时，它能够在较大的循环次数下依然保持高效的执行速度。

- **Groovy** 在不启用缓存的情况下，尤其是小循环次数时，性能不错。如果不需要缓存功能，Groovy 可能是一个合理的选择。

- **JavaScript** 在所有情况下性能都是最差的，除非有特定的需求或限制，否则建议避免使用 JavaScript 进行性能敏感的任务。

# 4. 进一步优化建议

- **评估缓存策略：** 根据实际应用场景，决定是否启用缓存。如果大部分操作是重复性的，且对初始化时间不敏感，那么启用缓存将带来显著的性能提升。

- **选择合适的脚本引擎：** 基于上述分析，JEXL 是性能最优的选择，特别是在需要处理大规模数据或频繁调用脚本的情况下。如果对性能要求不高，或者项目中有特定的依赖关系，可以选择 Groovy 或 JavaScript。

- **调整循环次数：** 如果你的应用场景允许，尽量减少不必要的大循环次数，以减轻性能负担。

### 规则引擎
#### ValueRule动态传参
| Benchmark                          | (loopSize) | Mode | Cnt | Score       | Error       | Units |
|------------------------------------|------------|------|-----|-------------|-------------|-------|
| RuleEngineBenchmark.testValueRule  | 100        | thrpt| 5   | 233212.153  | ± 98588.945 | ops/s |
| RuleEngineBenchmark.testValueRule  | 1000       | thrpt| 5   | 23936.416   | ± 13702.102 | ops/s |