FROM openjdk:11.0.12-jre-slim
MAINTAINER erickeuller@gmail.com
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]