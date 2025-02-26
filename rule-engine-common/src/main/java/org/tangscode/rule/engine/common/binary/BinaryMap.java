package org.tangscode.rule.engine.common.binary;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/17
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tangscode.rule.engine.common.exception.MetadataNotFoundException;

import java.nio.*;
import java.util.*;

public class BinaryMap {

    private static final Logger logger = LoggerFactory.getLogger(BinaryMap.class);

    // 存储数据的缓冲区
    private ByteBuffer buffer;
    // 存储类型元数据
    private final Map<String, EntryMeta> metadata = new LinkedHashMap<>();
    // 类型处理器注册器
    private final BinaryReaderWriterRegistry writerRegistry;

    // 初始化时注册默认类型处理器
    public BinaryMap(int capacity) {
        this(capacity, new BinaryReaderWriterRegistry());
    }

    public BinaryMap(int capacity, BinaryReaderWriterRegistry writerRegistry) {
        this.buffer = ByteBuffer.allocate(capacity).order(ByteOrder.BIG_ENDIAN);
        this.writerRegistry = writerRegistry;
    }

    // 核心API：设置value
    public <T> void setValue(String key, T value) {
        BinaryWriter<T> writer = writerRegistry.getWriter(value.getClass());
        int requiredCapacity = writer.calculateSize(value);
        ensureBufferCapacity(requiredCapacity);

        // 记录当前buffer位置作为元数据
        int offset = buffer.position();
        metadata.put(key, new EntryMeta(offset, value.getClass()));
        try {
            writer.write(buffer, offset, value);
        } catch (Exception e) {
            logger.info("buffer size:{} position:{} remain:{} requireCapacity: {}",
                    buffer.capacity(), buffer.position(), buffer.remaining(), requiredCapacity);
            throw e;
        }
    }

    // 核心API：获取value
    public <T> T getValue(String key) {
        EntryMeta meta = metadata.get(key);
        if (meta == null) {
            throw new MetadataNotFoundException("Metadata not found for key: " + key);
        }
        BinaryReader<T> reader = writerRegistry.getReader(meta.fieldType);
        return reader.read(buffer, meta.offset);
    }

    // 缓冲区动态扩容
    private void ensureBufferCapacity(int required) {
        if (buffer.remaining() < required) {
            // 缓冲区大小为当前大小的两倍或者当前大小加上所需大小
            int newCapacity = Math.max(buffer.capacity() << 1, buffer.capacity() + required);
            logger.info("binary buffer starts to scale capacity old:{} remain:{} required:{} new: {}",
                    buffer.capacity(), buffer.remaining(), required, newCapacity);
            ByteBuffer newBuffer = ByteBuffer.allocate(newCapacity).order(buffer.order());
            buffer.flip();
            newBuffer.put(buffer);
            buffer = newBuffer;
        }
    }

    // 类型元数据
    public static class EntryMeta {
        final int offset;
        final Class<?> fieldType;

        EntryMeta(int offset, Class<?> fieldType) {
            this.offset = offset;
            this.fieldType = fieldType;
        }
    }
}

