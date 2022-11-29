FROM maven:3.8.6-openjdk-18 as builder
WORKDIR .
COPY . .
RUN mvn package

FROM openjdk:18-ea-8-jdk-slim
WORKDIR application
ARG JAR_FILE=/target/*.jar

COPY --from=builder ${JAR_FILE} lightricks.jar
COPY ./run.sh /
RUN chmod +x /run.sh
#CMD java -jar lightricks.jar https://en.wikipedia.org 1 /output/en.wikipedia.org.tsv
ENTRYPOINT ["/run.sh"]


# RUN java -Djarmode=layertools -jar application.jar extract
# FROM openjdk:11-jre-slim
# WORKDIR application
# COPY – from=builder application/dependencies/ ./
# COPY – from=builder application/spring-boot-loader/ ./
# COPY – from=builder application/snapshot-dependencies/ ./
# COPY – from=builder application/application/ ./
# ENTRYPOINT ["java", "-jar", "drivebox.jar"]