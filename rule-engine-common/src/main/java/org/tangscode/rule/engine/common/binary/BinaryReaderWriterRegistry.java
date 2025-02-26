package org.tangscode.rule.engine.common.binary;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/19
 */
public class BinaryReaderWriterRegistry {
    // 定义类的读写实现
    private final Map<Class<?>, BinaryWriter<?>> writers = new ConcurrentHashMap<>();
    private final Map<Class<?>, BinaryReader<?>> readers = new ConcurrentHashMap<>();
    // 默认的读写器
    private BinaryReaderWriter fallbackReaderWriter;

    public BinaryReaderWriterRegistry() {
        registerBuiltinReadersAndWriters();
    }

    // 核心API：获取类型的写入器
    public <T> BinaryWriter<T> getWriter(Class<?> dataType) {
        BinaryWriter<T> writer = (BinaryWriter<T>) writers.get(dataType);
        if (writer == null) {
            // use fallback writer
            if (fallbackReaderWriter != null) {
                return fallbackReaderWriter;
            }
            throw new IllegalArgumentException("No writer for " + dataType);
        }
        return writer;
    }

    // 核心API：获取类型的读取器
    public <T> BinaryReader<T> getReader(Class<?> dataType) {
        BinaryReader<T> reader = (BinaryReader<T>) readers.get(dataType);
        if (reader == null) {
            // use fallback reader
            if (fallbackReaderWriter != null) {
                return fallbackReaderWriter;
            }
            throw new IllegalArgumentException("No reader for " + dataType);
        }
        return reader;
    }

    // 核心API：注册类型的写入器和读取器
    public <T> void register(Class<T> dataType, BinaryWriter<T> writer, BinaryReader<T> reader) {
        writers.put(dataType, writer);
        readers.put(dataType, reader);
    }

    // 注册默认的读写器，不是内置类型时用该读写器处理
    public void registerFallbackReaderWriter(BinaryReaderWriter readerWriter) {
        this.fallbackReaderWriter = readerWriter;
    }

    // 基础类型注册
    private void registerBuiltinReadersAndWriters() {
        // Short
        register(Short.class, new FixedSizeBinaryWriter<>(Short.BYTES, ByteBuffer::putShort), ByteBuffer::getShort);
        // Integer
        register(Integer.class, new FixedSizeBinaryWriter<>(Integer.BYTES, ByteBuffer::putInt), ByteBuffer::getInt);
        // Long
        register(Long.class, new FixedSizeBinaryWriter<>(Long.BYTES, ByteBuffer::putLong), ByteBuffer::getLong);

        // BigDecimal(长度变化）
        register(BigDecimal.class, new CustomReaderWriters.BigDecimalBinaryWriter(), new CustomReaderWriters.BigDecimalBinaryReader());

        // String
        register(String.class, new StringWriter(), new StringReader());
    }

    // 固定大小的写入器
    private static class FixedSizeBinaryWriter<T> implements BinaryWriter<T> {
        private final int size;
        private final TriConsumer<ByteBuffer, Integer, T> writer;

        public FixedSizeBinaryWriter(int size, TriConsumer<ByteBuffer, Integer, T> writer) {
            this.size = size;
            this.writer = writer;
        }

        @Override
        public int calculateSize(T value) {
            return size;
        }

        @Override
        public void write(ByteBuffer buffer, int offset, T value) {
            writer.accept(buffer, offset, value);
        }
    }

    private static class StringWriter implements BinaryWriter<String> {

        @Override
        public int calculateSize(String value) {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            return Short.BYTES + bytes.length;
        }

        @Override
        public void write(ByteBuffer buffer, int offset, String value) {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            buffer.putShort(offset, (short) bytes.length); // 长度头占两字节
            buffer.position(offset + Short.BYTES); // 移动到数据区
            buffer.put(bytes); // 写入数据
        }
    }

    private static class StringReader implements BinaryReader<String> {

        @Override
        public String read(ByteBuffer buffer, int offset) {
            short length = buffer.getShort(offset);
            byte[] bytes = new byte[length];
            buffer.position(offset + Short.BYTES);
            buffer.get(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }


}
