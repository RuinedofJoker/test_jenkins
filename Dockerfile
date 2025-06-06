FROM openjdk:8
COPY releasesDir/*.jar test_boot.jar
ENTRYPOINT ["java", "-jar", "test_boot.jar"]