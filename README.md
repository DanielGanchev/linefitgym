Description
LineFitGymApp is a Java-based platform designed as a meeting site where people in need can find quality coaches online. This fills a gap for those who lack access to professional coaching services nearby or seek specific expertise not available locally. Built with Spring Boot and Gradle, this application ensures robust backend management with a focus on scalability and performance.

Getting Started
Prerequisites
Java JDK 11 or newer
Gradle 7.0 (included via Gradle wrapper)
MySQL
Spring Framework 3.1.1

Installation and Running the Application


Navigate to the project directory and build the project:
bash
Copy code
./gradlew build

To start the application, use the following command:
bash
Copy code
./gradlew bootRun

Testing
To run automated tests for this system, execute:


./gradlew test

Configuration
Ensure to set up your environment variables or application.yml accordingly for a secure and functional setup:

Cloudinary: For image management within the application.
yaml
Copy code
cloudinary:
  cloud_name: [your_cloud_name]
  api_key: [your_api_key]
  api_secret: ${SECRET}

Spring Mail: For sending verification emails.

spring.mail:
  host: smtp.gmail.com
  port: 587
  username: ${MAIL_USERNAME}
  password: ${MAIL_PASSWORD}
  properties.mail.smtp.auth: true

DietaGram API: Limited to 10 fetches for fetching calories counts of different foods.
rapidapi:
  key: ${RAPIDAPI_KEY}

Spring Security: Default remember-me functionality setup.
spring:
  application:
    name: LineFitGymApp
  remember-me:
    key: ${REMEMBER_ME_KEY}

Database Configuration:
datasource:
  username: ${MYSQL_USER}
  password: ${MYSQL_PASSWORD}


API Endpoints
Details of the endpoints available in the application:

AdminController: Manages administrative functions.

GET /admin
GET /admin/all
GET /admin/search
DELETE /admin/delete

UserControllers: Handles user interactions.
GET /users/login
GET /users/login-error
GET /users
GET /users/register
POST /users/register
POST /users/register/verifyEmail
GET /users/update/{userId}

Additional Controllers: For blogs, food information, and more.
GET /blog
POST /blog/create/{userId}
DELETE /blog/delete/{id}
GET /food
GET /food/input
GET /food/table
