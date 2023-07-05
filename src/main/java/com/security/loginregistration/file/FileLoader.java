package com.security.loginregistration.file;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class FileLoader {

    private final ResourceLoader resourceLoader;

    public FileLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource loadFile(String fileName) {
        return resourceLoader.getResource("classpath:static/" + fileName);
    }
}