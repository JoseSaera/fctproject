FROM openjdk:22-jdk-slim
ARG JAR_FILE=target/movie-service-0.0.1.jar
COPY ${JAR_FILE} app_moviepedia.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_moviepedia.jar"]