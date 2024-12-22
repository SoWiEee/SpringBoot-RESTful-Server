# pull image
FROM openjdk:21-oracle

# set env
ENV APP_HOME=/app
RUN mkdir -p $APP_HOME

# copy jar & config to /app
COPY ["./target/*.jar", "$APP_HOME/app.jar"]
COPY ["./src/main/resources/application.properties", "$APP_HOME/application.properties"]

# set spring port
EXPOSE 8080

WORKDIR $APP_HOME

# run app
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=application.properties"]