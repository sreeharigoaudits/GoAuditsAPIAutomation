package com.goaudits.automation.framework.api.fileHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class PropertyFile {
    private final String filePath;
    private final Properties properties;

    public PropertyFile(String filePath) throws IOException {
        this.filePath = filePath;
        this.properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            create();
            properties.load(new FileInputStream(filePath));
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void create() throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    public void setProperty(String property, String value) {
        properties.setProperty(property, value);
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public Enumeration<Object> getAllProperties() {
        return properties.keys();
    }
}