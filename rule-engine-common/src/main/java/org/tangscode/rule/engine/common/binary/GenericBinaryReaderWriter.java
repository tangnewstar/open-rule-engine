package org.tangscode.rule.engine.common.binary;

import org.tangscode.rule.engine.common.exception.SerializationException;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/19
 */
public class GenericBinaryReaderWriter implements BinaryReaderWriter<Object> {

    @Override
    public Object read(ByteBuffer buffer, int offset) {
        int length = buffer.getInt(offset);
        byte[] data = new byte[length];
        buffer.position(offset + 4);
        buffer.get(data);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return ois.readObject();
        } catch (Exception e) {
            throw new SerializationException("Generic deserialization failed", e);
        }
    }

    // 缓存类型的内存大小，并设置过期时间自动过期
    private static Map<Class<?>, Integer> clazzSizeCache = new WeakHashMap<>();

    @Override
    public int calculateSize(Object value) {
        // if cached
        if (clazzSizeCache.containsKey(value.getClass())) {
            return clazzSizeCache.get(value.getClass());
        }
        System.out.println("starts to calculate size for type: " + value.getClass().getName());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(value);
            int size = baos.size();
            System.out.println("calculateSize for type: " + value.getClass().getName() + " size: " + size);
            clazzSizeCache.put(value.getClass(), size);
            return size; // 返回序列化后的字节长度
        } catch (IOException e) {
            throw new SerializationException("Size calculation failed", e);
        }
    }

    @Override
    public void write(ByteBuffer buffer, int offset, Object value) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(value);
            byte[] bytes = bos.toByteArray();
            buffer.putInt(offset, bytes.length);
            buffer.position(offset + 4);
            buffer.put(bytes);
        } catch (IOException e) {
            throw new SerializationException("Generic serialization failed", e);
        }
    }
}

