package com.mattstine.twelvefactor.springboot.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TemplateHealthCheck implements HealthIndicator {

    private final String template;

    @Autowired
    public TemplateHealthCheck(@Value("${template}") String template) {
        this.template = template;
    }

    @Override
    public Health health() {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Health.down().withDetail("error", "template doesn't include a name").build();
        }
        return Health.up().build();
    }
}
