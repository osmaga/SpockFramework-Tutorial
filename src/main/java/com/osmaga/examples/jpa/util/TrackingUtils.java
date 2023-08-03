package com.osmaga.examples.jpa.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.TraceContext;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrackingUtils {

    private final Tracer tracer;

    public String getCurrentTraceId() {
        return Optional.ofNullable(tracer.currentTraceContext()).map(CurrentTraceContext::context).map(TraceContext::traceId).orElse("");
    }

}
