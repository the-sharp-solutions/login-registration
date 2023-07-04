FROM eclipse-temurin:11
VOLUME /temp
RUN mkdir /opt/app
COPY target/*.jar /opt/app/japp.jar
ENTRYPOINT ["java", "-jar", "/opt/app/japp.jar"]
EXPOSE 8585