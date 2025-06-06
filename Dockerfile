FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY ./*.jar /test_boot.jar
ENTRYPOINT ["java", "-jar", "/test_boot.jar"]