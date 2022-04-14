# Technical Test

This Project is based on Spring Boot and Java 11, with following Features:
* Register a user
    * We suppose each user can be unique based on combination of name and birth date.
* Retrieve users based on name
* Retrieve user based on Id
    * id not exposed in the responses
    
## Technical Features

* Jpa
    * H2 Base
* logging
    * Spring Aop
* Documentation
    * Swagger ([link](http://localhost:8080/swagger-ui.html) for html page)
    * Postman collection ([link](./User%20Registery.postman_collection.json))
* Testing
    * Junit & Mockito
    * BDD : cucumber based on CucumberTest.java run
* Spring Validation

## Running the project

Build this project : mvn clean compile

launch Application.