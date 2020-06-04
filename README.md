 # Charging Session API
REST API for Charging session in stations. 

Operations covered,

| Operation       | Description    | [RequestType]:Request URL |
| :------------- | :---------- | :----------- |
| new session | Creates a new charging session  | [POST]:http://domain-name/charging-session-api/v1/chargingSessions |
| stop session   | Stops an existing session | [PUT]:http://domain-name/charging-session-api/v1/chargingSessions/{id} |
| retrieve all sessions   | Get all current charging sessions | [GET]:http://domain-name/charging-session-api/v1/chargingSessions |
| retrieve summary   | Get Summary of last minute charging sessions| [GET]:http://domain-name/charging-session-api/v1/chargingSessions/summary |

# Tools and Technologies used
  * [Spring Boot](https://spring.io/blog/2019/10/16/spring-boot-2-2-0)
  * [Java 8](https://docs.oracle.com/javase/8/docs/)
  * [Junit 5](https://junit.org/junit5/docs/current/user-guide/)
  * [Swagger 2.0](https://swagger.io/docs/specification/2-0/basic-structure/)
  * [Lombok](https://projectlombok.org/)

# How to Run and Test application

   [maven command to start directly : ] mvn spring-boot:run
   [maven command to start with tests : ] mvn -P run-with-tests
   Note: run-with-tests starts the application after running unit and concurrency tests
   
   Link : http://localhost:8080/charging-session-api/v1/chargingSessions
	
# Swagger Documentation 
  http://localhost:8080/charging-session-api/v1/swagger-ui.html#/

# To-Dos
    - Improved Logging
    - User authentication
# Team and Mailing Lists
    - praveen.itian@gmail.com
