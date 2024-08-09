FROM eclipse-temurin:21-jdk
EXPOSE 5666
COPY target/dig-backend*.jar app.jar
CMD ["java", "-cp", "app.jar", "dig.DigApplication"]