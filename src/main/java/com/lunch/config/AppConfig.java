package com.lunch.config;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration class to read data from properties file
 */
@Slf4j
public class AppConfig {

    private final Properties properties;

    @Inject
    public AppConfig() {
        this.properties = getProperties();
    }

    private Properties getProperties() {
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("service.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            log.error("Error reading properties file" + "service.properties", e);
        }

        return props;
    }

    public String getRecipesUrl() {
        return this.properties.getProperty("recipes.url");
    }

    public String getIngredientsUrl() {
        return this.properties.getProperty("ingredients.url");
    }

}
