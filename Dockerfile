FROM openjdk:17-alpine

EXPOSE 8080

ENV APP_HOME /usr/src/app

WORKDIR $APP_HOME

COPY target/*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
