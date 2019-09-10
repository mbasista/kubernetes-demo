FROM openjdk:8-alpine
ADD ./target/dbservice.jar ./app.jar
EXPOSE 8765
ENTRYPOINT ["java", "-jar", "app.jar"]