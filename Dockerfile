FROM openjdk:17-alpine

EXPOSE 8080

ENV APP_HOME /usr/src/app

COPY target/boardgame-0.0.1-SNAPSHOT.jar $APP_HOME/app.jar

WORKDIR $APP_HOME

CMD ["java", "-jar", "app.jar"]
