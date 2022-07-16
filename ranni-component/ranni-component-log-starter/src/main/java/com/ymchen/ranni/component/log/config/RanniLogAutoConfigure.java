package com.ymchen.ranni.component.log.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymchen.ranni.component.log.aspect.LogRecordAspect;
import com.ymchen.ranni.component.log.properties.RanniLogProperties;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@EnableConfigurationProperties(RanniLogProperties.class)
public class RanniLogAutoConfigure {

    @Value("${spring.application.name}")
    private String applicationName;

    private final RanniLogProperties ranniLogProperties;

    public RanniLogAutoConfigure(RanniLogProperties ranniLogProperties) {
        this.ranniLogProperties = ranniLogProperties;
    }

    private static final LoggerContext CONTEXT;
    private static final Logger ROOTLOGGER;

    static {
        CONTEXT = (LoggerContext) LoggerFactory.getILoggerFactory();
        ROOTLOGGER = CONTEXT.getLogger("ROOT");
    }

    @Bean
    @ConditionalOnProperty(name = "ranni.log.enableLogRecord", havingValue = "true", matchIfMissing = false)
    public LogRecordAspect logRecordAspect() {
        return logRecordAspect();
    }

    @ConditionalOnProperty(name = "ranni.log.sendToELK", havingValue = "true",matchIfMissing = false)
    @Bean
    public void enableElk() throws JsonProcessingException {
        LogstashTcpSocketAppender appender = new LogstashTcpSocketAppender();
        LogstashEncoder encoder = new LogstashEncoder();

        HashMap<String, String> customFields = new HashMap<>(2);
        customFields.put("application-name", applicationName);
        String customFieldsString = new ObjectMapper().writeValueAsString(customFields);
        encoder.setCustomFields(customFieldsString);

        appender.setEncoder(encoder);
        appender.addDestination(ranniLogProperties.getLogstashHost());
        appender.setName("logstash[" + applicationName + "]");
        appender.start();
        appender.setContext(CONTEXT);
        ROOTLOGGER.addAppender(appender);
    }
}
