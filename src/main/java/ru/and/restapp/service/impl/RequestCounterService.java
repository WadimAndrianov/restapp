package ru.and.restapp.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import ru.and.restapp.aspect.GeneralInterceptorAspect;

@Service
public class RequestCounterService {
    private final Counter requestCounter;

    private final Logger logger = LoggerFactory.getLogger(GeneralInterceptorAspect.class);

    public RequestCounterService(MeterRegistry meterRegistry) {
        this.requestCounter = Counter.builder("requests.api.total")
                .description("Total number of api-requests")
                .register(meterRegistry);
    }

    public synchronized void incrementRequestCounter() {
        requestCounter.increment();
        logger.info("Request counter incremented. Current count: {}", requestCounter.count());
    }
}