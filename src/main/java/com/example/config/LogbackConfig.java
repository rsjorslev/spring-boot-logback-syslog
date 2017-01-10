package com.example.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.net.SyslogAppender;
import com.example.model.SyslogServer;
import com.example.repository.SyslogServerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackConfig {

    @Autowired
    private SyslogServerRepository repository;

    @Bean
    public Logger logger() {

        // check to see if anything exists in the repository
        boolean serverExists = repository.findAll().stream().findFirst().isPresent();

        if (serverExists) {

            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

            // pick the first SyslogServer entry in the repo - not pretty but it works
            SyslogServer server = repository.findAll().stream().findFirst().get();

            SyslogAppender syslogAppender = new SyslogAppender();
            syslogAppender.setSyslogHost(server.getServer());
            syslogAppender.setPort(server.getPort());
            syslogAppender.setFacility(server.getFacility());
            syslogAppender.setContext(lc);
            syslogAppender.start();

            root.addAppender(syslogAppender);
        }

        return null;
    }
}
