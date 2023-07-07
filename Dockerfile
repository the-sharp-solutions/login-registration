#FROM eclipse-temurin:11
#VOLUME /temp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
#EXPOSE 8080

FROM eclipse-temurin:11-jdk as builder

ARG MAVEN_VERSION=3.9.3
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
