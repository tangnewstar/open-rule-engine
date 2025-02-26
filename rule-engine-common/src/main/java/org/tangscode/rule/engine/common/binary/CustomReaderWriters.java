package org.tangscode.rule.engine.common.binary;

import org.tangscode.rule.engine.common.exception.SerializationException;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/19
 */
public class CustomReaderWriters {

    public static class BigDecimalBinaryWriter implements BinaryWriter<BigDecimal> {

        @Override
        public int calculateSize(BigDecimal value) {
            // 动态计算长度，不缓存
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(value);
                return baos.size();
            } catch (IOException e) {
                throw new SerializationException("BigDecimal size calculation failed", e);
            }
        }

        @Override
        public void write(ByteBuffer buffer, int offset, BigDecimal value) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(value);
                byte[] bytes = bos.toByteArray();
                buffer.putInt(offset, bytes.length);
                buffer.position(offset + 4);
                buffer.put(bytes);
            } catch (IOException e) {
                throw new SerializationException("BigDecimal serialization failed", e);
            }
        }
    }

    public static class BigDecimalBinaryReader implements BinaryReader<BigDecimal> {

        @Override
        public BigDecimal read(ByteBuffer buffer, int offset) {
            int length = buffer.getInt(offset);
            byte[] data = new byte[length];
            buffer.position(offset + 4);
            buffer.get(data);
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
                return (BigDecimal) ois.readObject();
            } catch (Exception e) {
                throw new SerializationException("BigDecimal deserialization failed", e);
            }
        }
    }
}
