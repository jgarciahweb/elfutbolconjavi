package com.jgarciahweb.elfutbolconjavi.infrastructure;

import com.jgarciahweb.elfutbolconjavi.infrastructure.services.UserMetricService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class UserMetricServiceTest {

    private Counter counter;

    private UserMetricService userMetricService;

    @BeforeEach
    void setUp() {
        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        counter = mock(Counter.class);
        Counter.Builder builder = mock(Counter.Builder.class);

        given(builder.description(anyString())).willReturn(builder);
        given(builder.register(meterRegistry)).willReturn(counter);

        MockedStatic<Counter> counterMock = mockStatic(Counter.class);
        counterMock.when(() -> Counter.builder(anyString()))
                .thenReturn(builder);

        userMetricService = new UserMetricService(meterRegistry);
    }

    @Test
    void shouldIncrementUserRegisteredCounter() {
        userMetricService.incrementUserRegistered();

        then(counter).should(times(1)).increment();
    }
}