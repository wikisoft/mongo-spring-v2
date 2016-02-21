package com.rac.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.rac" })
@Import({ SpringMongoConfig.class, WebConfig.class })
@Configuration
public class AppConfig {

}
