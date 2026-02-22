# ─── Build stage ──────────────────────────────────────────────────────────────
FROM maven:3.9.12-eclipse-temurin-25-alpine AS builder

WORKDIR /workspace

# Copy Maven wrapper and root POM first for layer-cache efficiency
COPY .mvn/                  .mvn/
COPY mvnw mvnw.cmd pom.xml ./
COPY domain/pom.xml         domain/pom.xml
COPY application/pom.xml    application/pom.xml
COPY infraestructure/pom.xml infraestructure/pom.xml
COPY boot/pom.xml           boot/pom.xml
COPY api-first/pom.xml      api-first/pom.xml
COPY api-first/bc-rest-api/pom.xml api-first/bc-rest-api/pom.xml

# Download dependencies (cached as long as POMs don't change)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -q

# Copy source code
COPY domain/src         domain/src
COPY application/src    application/src
COPY infraestructure/src infraestructure/src
COPY boot/src           boot/src
COPY api-first/bc-rest-api/src api-first/bc-rest-api/src

# Build the fat JAR (skipping tests)
RUN ./mvnw clean package -pl boot -am -DskipTests -q

# Extract Spring Boot layers for optimal Docker caching
RUN java -Djarmode=layertools -jar boot/target/*.jar extract --destination /workspace/layers

# ─── Runtime stage ────────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copy layers in dependency-stability order (most stable → least stable)
COPY --from=builder /workspace/layers/dependencies/          ./
COPY --from=builder /workspace/layers/spring-boot-loader/    ./
COPY --from=builder /workspace/layers/snapshot-dependencies/ ./
COPY --from=builder /workspace/layers/application/           ./

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=pro

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
