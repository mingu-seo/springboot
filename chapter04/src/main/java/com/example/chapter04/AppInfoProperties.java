package com.example.chapter04;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.info")
@Getter
@Setter
public class AppInfoProperties {
    private String name;
    private String version;
}