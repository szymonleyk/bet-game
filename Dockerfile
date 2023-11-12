FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/bet-game-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]