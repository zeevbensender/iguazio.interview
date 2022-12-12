FROM maven:3.8.6-openjdk-18 as builder
WORKDIR .
COPY . .
RUN mvn package

FROM openjdk:18-ea-8-jdk-slim
WORKDIR application
ARG JAR_FILE=/target/*.jar

COPY --from=builder ${JAR_FILE} iguazio.jar
COPY ./run.sh /
RUN chmod +x /run.sh
ENTRYPOINT ["/run.sh"]