package org.tangscode.rule.engine.common.exception;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/19
 */
public class SerializationException extends RuntimeException {

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
