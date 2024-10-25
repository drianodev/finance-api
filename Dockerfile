FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:21-jdk

EXPOSE 8080

COPY --from=build /target/finance-api-0.0.1-SNAPSHOT.jar /app/finance-api.jar

ENTRYPOINT ["java", "-jar", "/app/finance-api.jar"]