package org.tangscode.rule.engine.core.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author tangxinxing
 * @version 1.0
 * @description
 * @date 2025/2/17
 */
public class ResourceUtil {

    public static String readResourceFile(String fileName) throws IOException {
        return IOUtils.toString(ResourceUtil.class.getClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8.toString());
    }
}
