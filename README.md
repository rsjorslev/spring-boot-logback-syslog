# spring-boot-logback-syslog

Simple Spring Boot application that shows how to add and detach appenders (SyslogAppender in this example) at runtime.

Inside the main class `DemoApplication` there is a `@Scheduled` annotated method (`logRandomStuff()`) which runs every 10seconds to just generate some logger output to test with.

## Instructions

To add a syslog server you can POST to `http://localhost:8080/syslogserver` with a body like the below:


    {
        "server": "my.syslogserver.com",
        "port": 514,
        "facility": "auth"
    }

This will persist the SyslogServer to the file based H2 database being used (which resides in ./data/demo.*) and also create the SyslogAppender, set the context (default), start it and then add it to the root logger.

To delete a SyslogServer perform a DELETE `http://localhost:8080/syslogserver/{id}` where ID is the returned ID in the response body from the POST above or by performing a GET to `http://localhost:8080/syslogserver`.
Performing the DELETE will first stop the appender on the root logger and then detach it.
