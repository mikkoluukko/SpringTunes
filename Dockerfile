FROM openjdk:15
ADD target/___ app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]