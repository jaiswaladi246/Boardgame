#!/bin/bash
echo "Starting Java application..."
chmod +x /opt/database_service_project/database_service_project-0.0.4.jar
nohup java -jar /opt/database_service_project/database_service_project-0.0.4.jar >/opt/database_service_project/app.log 2>&1 &
echo "Application started!"
