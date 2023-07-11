FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/serviceactivity*.jar /app.jar
EXPOSE 8084
CMD ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]