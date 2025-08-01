FROM openjdk:17-alpine
        
EXPOSE 8080
 
ENV APP_HOME /usr/src/app

#COPY target/*.jar $APP_HOME/app.jar
COPY target/*.jar $APP_HOME/

WORKDIR $APP_HOME

CMD ["java", "-jar", "app.jar"]
