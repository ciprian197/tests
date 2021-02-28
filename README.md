
This repository is an example application for using several technologies from Java ecosystem.

# Existing features:
* Custom filtering and pagination with custom response from database (if your goal is just to retrieve plain entities from the database then I recommend taking a look at [QuerydslPredicateExecutor](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QuerydslPredicateExecutor.html))
* Testing using JUnit 5

# Features that might follow
* Security using Keycloak - integrating access logic in queries for resource access management
* GraphQL endpoints 
* Integration tests with [TestContainers](https://www.testcontainers.org)

# Getting Started
* Build project: **./gradlew clean build**
* Start local environmet:**docker-compose up -d** - starts database on port 3306
* Run application:**./gradlew clean bootRun**

# Examples 
* Filter cars: 
<br>**curl --location --request GET 'http://localhost:8080/car-project/api/v1/cars?ids=3f0098fe-1460-477f-9e5b-1bda44c6a2f6,93e23139-3d3d-4fbf-afb5-f1cb37c5fe3c&pageNumber=1&pageSize=10&brandIds=d73acdee-791e-11eb-b46c-0242ac130002&sortOptions%5B0%5D.by=BRAND_NAME&sortOptions%5B0%5D.order=DESC&sortOptions%5B1%5D.by=NAME&sortOptions%5B1%5D.order=ASC'**
