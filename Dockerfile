FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/genAiMicroservice-0.0.1-SNAPSHOT.jar app.jar

RUN mkdir -p /app/downloads

ENV WORKING_DIRECTORY=/app/downloads

EXPOSE 8182

CMD ["java", "-jar", "app.jar"]