#!/bin/bash
echo "Stopping Java application..."
pkill -f 'java -jar' || echo "No running Java application found"
echo "Java application stopped."
