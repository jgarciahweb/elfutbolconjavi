package com.jgarciahweb.elfutbolconjavi.infrastructure.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class UserMetricService {
    private final Counter userRegisteredCounter;

    public UserMetricService(MeterRegistry meterRegistry) {
        this.userRegisteredCounter = Counter.builder("app_users_registered_total")
                .description("Total de usuarios registrados")
                .register(meterRegistry);
    }

    public void incrementUserRegistered() {
        userRegisteredCounter.increment();
    }
}
