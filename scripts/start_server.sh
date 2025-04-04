#!/bin/bash
echo "Starting Java application..."
nohup java -jar /home/ec2-user/myapp/app.jar >/home/ec2-user/myapp/app.log 2>&1 &
echo "Java application started."
