#!/bin/bash
echo "Deploying Java application..."
cp /home/ec2-user/myapp/myapp.jar /home/ec2-user/myapp/app.jar
chmod +x /home/ec2-user/myapp/app.jar
