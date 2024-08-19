FROM maven:3.9-eclipse-temurin-21 AS builder
COPY . .
RUN mvn clean package -Pjar -Dmaven.test.skip

FROM eclipse-temurin:21-jre-jammy
EXPOSE 5666
COPY --from=builder target/dig-backend*.jar app.jar
CMD ["java", "-cp", "app.jar", "dig.DigApplication"]