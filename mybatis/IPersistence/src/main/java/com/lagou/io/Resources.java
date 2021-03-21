package com.lagou.io;

import java.io.InputStream;

/**
 * @author ying
 * @version 1.0
 * @date 2021-01-24 00:44
 */
public class Resources {

    /**
     * 根据配置文件路径，将配置文件加载成输入流，存储在内存中
     *
     * @param path 配置文件路径
     * @return 输入流
     */
    public static InputStream getResourcesAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
