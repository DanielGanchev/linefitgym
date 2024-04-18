
# LineFitGymApp

## Introduction
**LineFitGymApp** is a robust Java-based platform developed to bridge the gap between fitness enthusiasts and professional coaching services. Utilizing advanced technologies like Spring Boot and Gradle, this application offers a convenient meeting site where users can connect with quality coaches online. Whether you're in a remote location or seeking specialized expertise, LineFitGymApp makes professional coaching accessible to everyone.



## Getting Started

### Prerequisites
- Java JDK 11 or newer
- Gradle 7.0 (included via Gradle wrapper)
- MySQL
- Spring Framework 3.1.1

### Installation and Running the Application

Clone the repository and navigate to the project directory. Then, build the project using:
```bash
./gradlew build
```

To start the application, use:
```bash
./gradlew bootRun
```

### Testing
Run automated tests with:
```bash
./gradlew test
```

## Configuration
Setup your environment variables or `application.yml` to match the configuration below for optimal functionality:

**Cloudinary Configuration for Image Management:**
```yaml
cloudinary:
  cloud_name: [your_cloud_name]
  api_key: [your_api_key]
  api_secret: ${SECRET}
```

**Spring Mail Setup for Email Verification:**
```yaml
spring.mail:
  host: smtp.gmail.com
  port: 587
  username: ${MAIL_USERNAME}
  password: ${MAIL_PASSWORD}
  properties.mail.smtp.auth: true
```

**API Limits and Security with Spring Security:**
```yaml
rapidapi:
  key: ${RAPIDAPI_KEY}
spring:
  application:
    name: LineFitGymApp
  remember-me:
    key: ${REMEMBER_ME_KEY}
datasource:
  username: ${MYSQL_USER}
  password: ${MYSQL_PASSWORD}
```

## API Endpoints
Explore the various functionalities of our application through these endpoints:

### AdminController
- `GET /admin`
- `GET /admin/all`
- `GET /admin/search`
- `DELETE /admin/delete`

### UserControllers
- `GET /users/login`
- `GET /users/login-error`
- `GET /users`
- `GET /users/register`
- `POST /users/register`
- `POST /users/register/verifyEmail`
- `GET /users/update/{userId}`

### Additional Controllers
- `GET /blog`
- `POST /blog/create/{userId}`
- `DELETE /blog/delete/{id}`
- `GET /food`
- `GET /food/input`
- `GET /food/table`

