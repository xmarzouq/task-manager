FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /build

COPY .mvn/ ./.mvn/
COPY mvnw mvnw.cmd pom.xml ./

RUN chmod +x ./mvnw

COPY src/ ./src/

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

RUN groupadd -r taskmanager && \
    useradd -r -g taskmanager -d /home/taskmanager -m taskmanager && \
    mkdir -p /home/taskmanager/.config && \
    chown -R taskmanager:taskmanager /home/taskmanager

COPY --from=build /build/target/*.jar app.jar

COPY scripts/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

RUN apt-get update && \
    apt-get install -y --no-install-recommends postgresql-client curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

RUN chown -R taskmanager:taskmanager /app
RUN chown taskmanager:taskmanager /entrypoint.sh

USER taskmanager

ENV SPRING_PROFILES_ACTIVE=docker
ENV SERVER_PORT=8080
ENV SERVER_SERVLET_CONTEXT_PATH=/task-manager
ENV HOME=/home/taskmanager

EXPOSE ${SERVER_PORT}

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:${SERVER_PORT}${SERVER_SERVLET_CONTEXT_PATH}/health || exit 1

ENTRYPOINT ["/entrypoint.sh"]

CMD ["java", "-jar", "app.jar"]