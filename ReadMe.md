# Technical Test

This Project is based on Spring Boot and Java 11, with following Features:
* Register a user
    * We suppose each user can be unique based on combination of name ,birth date And country.
* Retrieve users based on name
* Retrieve user based on Id
    * id not exposed in the responses
    
## Architecture

The Database Model include Three Tables :
* Country : include all countries , identified by country code
* Country_allowed : include countries that can register a user (have a OneToOne relation with Country Table)
* User : include user details ( ManyToOne with Country Table)
    
## Technical Features

* Jpa
    * H2 Base [console](http://localhost:8080/h2-console)
* logging
    * Spring Aop
* Documentation
    * Swagger ([link](http://localhost:8080/swagger-ui.html) for html page)
    * Postman collection [link](./User%20Registery.postman_collection.json)
* Testing
    * Junit & Mockito
    * BDD : cucumber based on [CucumberTest.java](./src/test/java/com/registry/bdd/CucumberTest.java) run
        * For Intellij add [package](https://plugins.jetbrains.com/plugin/7212-cucumber-for-java)
        * Scenario is under [registeruser.feature](./src/test/resources/registeruser.feature)
* Spring Validation

## Running the project

Build and run Jar

`mvn clean package`

`java -jar target\technical-assessment-0.0.1-SNAPSHOT.jar`