FROM adoptopenjdk/openjdk11 
      
EXPOSE 8080
 
ENV APP_HOME /usr/src/app

COPY artifact/*.jar $APP_HOME/app.jar

WORKDIR $APP_HOME

CMD ["java", "-jar", "app.jar"]
