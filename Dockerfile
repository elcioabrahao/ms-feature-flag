FROM amazoncorretto:21-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/ms-feature-flag-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]