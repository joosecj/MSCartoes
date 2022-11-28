FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-ea-3-jdk-oraclelinux7
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ARG RABBITMQ_SERVER=rabbitmq-host
ARG APP_EUREKA_LOGIN=cursoms-eureka-user
ARG APP_EUREKA_PASSORD=rsoe-dbmgcsqmou

ARG EUREKA_SERVER=localhost

ENTRYPOINT java -jar -Dspring.profiles.active=prod app.jar