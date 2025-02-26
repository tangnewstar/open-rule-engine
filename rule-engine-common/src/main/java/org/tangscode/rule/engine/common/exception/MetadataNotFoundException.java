package org.tangscode.rule.engine.common.exception;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/19
 */
public class MetadataNotFoundException extends RuntimeException {

    public MetadataNotFoundException(String message) {
        super(message);
    }

    public MetadataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
