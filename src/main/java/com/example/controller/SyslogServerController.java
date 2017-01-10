package com.example.controller;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.net.SyslogAppender;
import com.example.model.SyslogServer;
import com.example.repository.SyslogServerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/syslogserver")
public class SyslogServerController {

    @Autowired
    private SyslogServerRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SyslogServer>> getAllSyslogServers() {

        return ResponseEntity
                .ok()
                .body(repository.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addSyslogServer(@RequestBody @Valid SyslogServer syslogServer) {
        repository.save(syslogServer);

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        SyslogAppender syslogAppender = new SyslogAppender();
        syslogAppender.setName("MY_SYSLOG_APPENDER");
        syslogAppender.setSyslogHost(syslogServer.getServer());
        syslogAppender.setPort(syslogServer.getPort());
        syslogAppender.setFacility(syslogServer.getFacility());
        syslogAppender.setContext(lc);
        syslogAppender.start();

        root.addAppender(syslogAppender);

        return ResponseEntity.ok().body(syslogServer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSyslogServer(@PathVariable Long id) {

        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.getAppender("MY_SYSLOG_APPENDER").stop();
        root.detachAppender("MY_SYSLOG_APPENDER");

        repository.delete(id);

        return ResponseEntity.ok(null);

    }


}
