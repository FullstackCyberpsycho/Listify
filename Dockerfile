FROM openjdk:24-jdk-slim
WORKDIR /app
COPY target/listify-0.0.1-SNAPSHOT.jar /app/listify.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "listify.jar"]