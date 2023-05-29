# hn-statements-api

The **hn-statements-api** microservice is responsible for providing statement path details from database and FTP location.

## Built with

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Java](https://www.java.com/en/)

## Prerequisites

Before you begin, ensure you have met the following requirements:
* You have installed JDK 17
* Maven

## Setup

To install `hn-statements-api`, follow these steps:

Linux/macOS/Windows:
* Clone the project by running the following command in your preferred directory:
```
git clone git@github.com:hn-coreservices/hn-statements-api.git

```
If there are any issues in authentication using your Git creds, please create and use a [Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) as your password instead.

* Open the directory `hn-statements-api` on your preferred IDE
* Open the file `src/main/java/com/herbalife/dsmaint/HnStatementsAPIApplication.java` and click the run button next to the main method to start the application
* After a few seconds, the logs should show that the server has been started
* * The app is up and running on:
```
http://localhost:8080/hn-statements-api/
```

### Environment variables

The application needs some configuration values provided at runtime.
To see the values required, look at the [application-k8s.properties](src/main/resources/application-k8s.properties).

# Postman Collection
[Statement Path Details Postman Collection](src/main/resources/postman/Statement-Path-Details.postman_collection.json)

## API Documentation
[Statement Path Details Open API Spec](src/main/resources/swagger/statement-path-details.yaml)
