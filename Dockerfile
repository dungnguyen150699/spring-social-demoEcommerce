FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=target/my-spring-social.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
