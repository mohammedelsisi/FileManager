# Build
FROM maven:3.8.4-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Run
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/stc-1.0.0.jar /usr/local/lib/stc.jar
WORKDIR /usr/local/lib
ENTRYPOINT ["java","-jar","stc.jar"]