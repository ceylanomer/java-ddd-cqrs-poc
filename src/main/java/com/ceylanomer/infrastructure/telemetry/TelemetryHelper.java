package com.ceylanomer.infrastructure.telemetry;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Helper class for manual telemetry instrumentation
 * This class provides methods to manually instrument code with OpenTelemetry
 */
@Component
@RequiredArgsConstructor
public class TelemetryHelper {

    private final Tracer tracer;

    /**
     * Traces a database operation with the given name and attributes
     * @param operationName The name of the operation
     * @param attributes Additional attributes to add to the span
     * @param operation The operation to trace
     * @return The result of the operation
     */
    public <T> T traceDbOperation(String operationName, Map<String, String> attributes, Supplier<T> operation) {
        Span span = tracer.spanBuilder(operationName)
                .setSpanKind(SpanKind.CLIENT)
                .setAttribute("db.type", "sql")
                .setAttribute("db.operation", operationName)
                .startSpan();
        
        // Add custom attributes
        if (attributes != null) {
            attributes.forEach(span::setAttribute);
        }
        
        try (Scope scope = span.makeCurrent()) {
            T result = operation.get();
            span.setStatus(StatusCode.OK);
            return result;
        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR);
            span.recordException(e);
            throw e;
        } finally {
            span.end();
        }
    }

    /**
     * Traces a database operation that can throw checked exceptions
     * @param operationName The name of the operation
     * @param attributes Additional attributes to add to the span
     * @param operation The operation to trace
     * @return The result of the operation
     */
    public <T> T traceDbOperationWithException(String operationName, Map<String, String> attributes, Callable<T> operation) throws Exception {
        Span span = tracer.spanBuilder(operationName)
                .setSpanKind(SpanKind.CLIENT)
                .setAttribute("db.type", "sql")
                .setAttribute("db.operation", operationName)
                .startSpan();
        
        // Add custom attributes
        if (attributes != null) {
            attributes.forEach(span::setAttribute);
        }
        
        try (Scope scope = span.makeCurrent()) {
            T result = operation.call();
            span.setStatus(StatusCode.OK);
            return result;
        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR);
            span.recordException(e);
            throw e;
        } finally {
            span.end();
        }
    }
} 