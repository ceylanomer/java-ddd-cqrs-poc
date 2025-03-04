# Build stage
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Run stage
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Download OpenTelemetry Java agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar

ENTRYPOINT ["java", \
            "-javaagent:/app/opentelemetry-javaagent.jar", \
            "-Dotel.service.name=java-ddd-cqrs-poc", \
            "-Dotel.traces.exporter=otlp", \
            "-Dotel.metrics.exporter=otlp", \
            "-Dotel.exporter.otlp.endpoint=http://otel-collector:4317", \
            "-cp", "/app:/app/lib/*", \
            "com.ceylanomer.JavaDddCqrsPocApplication"]
