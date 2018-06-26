## Todo(s) Cache  

Howdy and welcome...you can never have enough cache so to speak.  Inevitably you'll need to implement Microservices to cache data for many purposes.  There's several common cache access patterns such as Cache-Aside, Read-Through, Write-Through and Write-Behind.  Another pattern is Caching as a System of Record where Microservices fuse onto a distributed cache (such as a grid like Gemfire).  This simplifies application architecture because it doesn't need to know of anything but the "grid"...any and all Microservices tap into the "grid" for reading/writing data.  The "grid" handles the rest :smile:

### Primary dependencies

* Spring Boot Starter Web (Embedded Tomcat)
* Spring Boot Starter Redis
* Spring Boot Actuators (ops endpoints)
* Spring Cloud Netflix Eureka Client (service discovery)
* Spring Cloud Config Client (central config)
* Spring Cloud Sleuth (request tracing)
* Spring Data Rest (blanket our Redis model in a REST API)
* Flyway (Database schema migration)
* Swagger (API documentation)


See this issue when deploying to cf
https://github.com/spring-guides/gs-messaging-redis/issues/3