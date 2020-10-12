package com.lunch;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.lunch.client.DataClient;
import com.lunch.config.AppConfig;
import com.lunch.config.DataConfig;
import com.lunch.endpoint.LunchEndpoint;
import com.lunch.service.LunchService;
import com.lunch.service.LunchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.ClientBuilder;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
public class WebApplication extends AbstractModule {

    @Override
    protected void configure() {
        initLogback();
        bind(LunchEndpoint.class);
        bind(LunchService.class).to(LunchServiceImpl.class);
        bind(AppConfig.class).asEagerSingleton();
        bind(DataConfig.class).asEagerSingleton();
    }

    private void initLogback() {
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

    @Provides
    @Singleton
    public EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence-unit");
        return factory.createEntityManager();
    }

    @Provides
    @Singleton
    public DataClient getClient() {
        return new DataClient(ClientBuilder.newClient());
    }

}
