BoardgameListingWebApp
Description
A full-stack web application that serves as a board game database, allowing users to view, add, edit, and review board games. This repository contains both application code and infrastructure as code for deployment on AWS and CI/CD.

Technologies Used
Application Stack
Java and Spring Boot for backend logic and REST API
Thymeleaf, HTML5, CSS, JavaScript for front-end and UI
JDBC with H2 Database Engine for database operations
Spring Security for authentication and authorization
JUnit for testing
Bootstrap for styling
Deployment and CI/CD
Amazon Web Services (AWS): EC2, S3, IAM, CloudFormation, RDS, CloudWatch, CloudTrail, CloudFront, DynamoDB
GitHub and GitHub Actions for version control and CI/CD
Jenkins and Bitbucket for automated builds
Ansible for configuration management
Docker for containerization
AWS CodePipeline and CodeCommit for CI/CD
Features
User Authentication: Secure login and permissions using Spring Security
Role-based Authorization: Different permissions for non-members, users, and managers
AWS Integration: Deployed on AWS EC2 with an automated infrastructure setup
Automated Testing: JUnit for unit tests
Automated Deployment: GitHub Actions and Jenkins pipeline for CI/CD
Monitoring: Integrated with CloudWatch for logging and alerts
AWS Services Overview
EC2: Hosting the application server
S3: Storing assets and backups
IAM: Access management
CloudFormation: Infrastructure as Code (IaC) for provisioning resources
RDS: Database service
CloudWatch and CloudTrail: Logging and monitoring
DynamoDB: Optional NoSQL database
CodePipeline and CodeCommit: CI/CD management
How to Run Locally
Clone the repository
Open the project in an IDE (e.g., IntelliJ, Eclipse)



Detailed Explanation of Added Files
Ansible: Place Ansible playbooks in the ansible/playbooks/ directory to handle configuration tasks, like setting up environments or deploying application updates.

CI/CD (GitHub Actions): Add a workflow file (deploy.yml) under .github/workflows to define CI/CD jobs, e.g., building the project, running tests, deploying to AWS EC2.

CloudFormation: Add templates under cloudformation/templates/ to set up AWS resources. For example, a boardgame-app.yml template could automate the creation of EC2 instances, S3 buckets, and an RDS database.

Jenkinsfile: This file defines your Jenkins pipeline, including steps to build, test, and deploy the application. If you’re using CodePipeline, the Jenkinsfile can be adjusted to integrate with it.

Dockerfile: Include a Dockerfile to containerize the application. Define the base image, dependencies, and entry point for easy deployment on different platforms.

Scripts: Add scripts/deploy.sh and scripts/setup.sh for any deployment or server setup needs.

Additional Configuration
ServiceNow Integration: If ServiceNow ticketing or monitoring is required, add any API calls or integration scripts in a separate servicenow/ directory or as part of your deployment scripts.

Service Level Agreements (SLA): Use CloudWatch and CloudTrail to monitor SLAs. Create alerts for when thresholds are exceeded and include SLA compliance guidelines in documentation.
