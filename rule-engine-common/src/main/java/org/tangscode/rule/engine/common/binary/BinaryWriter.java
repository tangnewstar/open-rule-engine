package org.tangscode.rule.engine.common.binary;

import java.nio.ByteBuffer;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/19
 */
//@FunctionalInterface
public interface BinaryWriter<T> {
    /**
     * 计算类型的字节大小
     * @param value
     * @return
     */
    int calculateSize(T value);

    /**
     * 写入值到缓冲区
     * @param buffer
     * @param offset
     * @param value
     */
    void write(ByteBuffer buffer, int offset, T value);
}
