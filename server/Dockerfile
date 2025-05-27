#FROM eclipse-temurin:17-jdk-alpine
#WORKDIR /app
#COPY . .
#RUN ./mvnw package -DskipTests
#CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]


# Use a multi-stage build for smaller final image
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
# Ensure mvnw is executable and has Unix line endings (LF)
RUN apk add --no-cache dos2unix && \
    dos2unix mvnw && \
    chmod +x mvnw && \
    ./mvnw package -DskipTests

# Final stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]