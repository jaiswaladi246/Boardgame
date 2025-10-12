## Builder stage: compile application using Maven and JDK 21
FROM maven:3.9.11-eclipse-temurin-21 as builder

WORKDIR /build

# Copy source and pom
COPY pom.xml mvnw ./
COPY src ./src

# Use Maven to package the application (skip tests for faster builds)
RUN mvn -B -DskipTests package

## Runtime stage: smaller Java 21 runtime image
FROM eclipse-temurin:21-jre-alpine

EXPOSE 8080

ENV APP_HOME /usr/src/app

# Copy the built jar from the builder image
COPY --from=builder /build/target/*.jar $APP_HOME/app.jar

WORKDIR $APP_HOME

CMD ["java", "-jar", "app.jar"]

