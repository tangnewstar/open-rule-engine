package org.tangscode.rule.engine.common;

import org.tangscode.rule.engine.common.binary.BinaryMap;
import org.tangscode.rule.engine.common.binary.BinaryReaderWriterRegistry;
import org.tangscode.rule.engine.common.binary.GenericBinaryReaderWriter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/19
 */
public class BinaryMapTest {

    private BinaryMap binaryMap;

    public BinaryMapTest() {
        BinaryReaderWriterRegistry registry = new BinaryReaderWriterRegistry();
        registry.registerFallbackReaderWriter(new GenericBinaryReaderWriter());
        binaryMap = new BinaryMap(1024, registry);
    }

    public static void main(String[] args) {
        BinaryMapTest test = new BinaryMapTest();
        test.testPrimitiveTypeRoundTrip();
        test.testUnregisteredType();
        test.testHashMapPerformance();
        test.testBinaryContextPerformance();
    }

    public void testPrimitiveTypeRoundTrip() {
        // 写入测试
        binaryMap.setValue("age", 18);
        binaryMap.setValue("processId", 1000L);
        binaryMap.setValue("name", "unknown");

        // 读取验证
        System.out.println("age: " + binaryMap.getValue("age"));
        System.out.println("processId: " + binaryMap.getValue("processId"));
        System.out.println("name: " + binaryMap.getValue("name"));
    }

    public void testUnregisteredType() {
        Double amount = 100.00;
        binaryMap.setValue("amount", amount); // 未注册Double类型
        Double value = binaryMap.getValue("amount");
        System.out.println("get amount: " + value);
        BigDecimal price = BigDecimal.valueOf(18.001);
        binaryMap.setValue("price", price);
        System.out.println("get price: " + binaryMap.getValue("price"));
    }

    // 测试get和set的性能
    public void testBinaryContextPerformance() {
        // 测试读写10k次读写耗时
        long start = System.nanoTime();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            // 生成随机字符串
            char randomChar = (char) ('a' + random.nextInt(26));
            binaryMap.setValue(String.valueOf(randomChar), 10 + i);
        }
        System.out.println("10k BinaryMap set elapsed: " + (System.nanoTime() - start) / 1000000.0);
    }

    //测试HashMap的读写性能
    public void testHashMapPerformance() {
        // 测试读写10k次读写耗时
        HashMap map = new HashMap();
        long start = System.nanoTime();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            // 生成随机字符串
            char randomChar = (char) ('a' + random.nextInt(26));
            map.put(String.valueOf(randomChar), 10 + i);
        }
        System.out.println("10k HashMap set elapsed: " + (System.nanoTime() - start) / 1000000.0);
    }
}
