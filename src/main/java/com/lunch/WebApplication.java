package com.lunch;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.google.inject.AbstractModule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Objects;

@Slf4j
public class WebApplication extends AbstractModule {

    @Override
    protected void configure() {
        log.info("Configure");
        initLogback();
    }

    protected void initLogback() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream configStream = null;
        try {
            configStream = classLoader.getResourceAsStream("logback.xml");
            configurator.setContext(loggerContext);
            configurator.doConfigure(configStream);
        } catch(Exception ex) {
            log.error("Error loading logback config");
        } finally {
            try {
                if (Objects.nonNull(configStream)) {
                    configStream.close();
                }
            } catch (Exception ex) {
                log.error("Error closing input stream", ex);
            }
        }
    }

}
