package com.github.murilonerdx.skdemoparkapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class SpringConfigLocaleConfig {


    @PostConstruct
    public void timezoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

}
