FROM openjdk:8-jdk-alpine
COPY target/*.jar webapp-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/webapp-1.0-SNAPSHOT.jar"]