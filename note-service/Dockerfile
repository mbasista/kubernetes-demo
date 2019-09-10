FROM openjdk:8-alpine
ADD ./target/note-service.jar /app.jar
EXPOSE 8901
ENTRYPOINT ["java", "-jar", "app.jar"]