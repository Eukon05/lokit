FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
ARG SERVICE=identity-service

COPY pom.xml .
COPY role-service/pom.xml role-service/
COPY room-service/pom.xml room-service/
COPY card-service/pom.xml card-service/
COPY device-service/pom.xml device-service/
COPY decision-service/pom.xml decision-service/
COPY identity-service/pom.xml identity-service/

COPY common common
COPY common-security common-security
COPY $SERVICE $SERVICE

RUN mvn -pl $SERVICE -am package -DskipTests

FROM eclipse-temurin:21-jre-jammy
ARG SERVICE=identity-service

COPY --from=build $SERVICE/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]