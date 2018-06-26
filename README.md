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
* Swagger (API documentation)

### Build

```bash
> git clone https://github.com/corbtastik/todos-cache.git
> cd todos-cache
> ./mvnw clean package
```

### Run

```bash
# assumes default redis address:port, localhost:6379
> java -jar target/todos-cache-1.0.0.SNAP.jar

# customize redis connection
> java -jar target/todos-cache-1.0.0.SNAP.jar \
  --spring.redis.port=9090
```

### Run with Remote Debug

```bash
> java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9111,suspend=n \
  -jar target/todos-cache-1.0.0.SNAP.jar
```

### Verify

By default Todo(s) Cache starts up on ``localhost:8002`` and like all Microservices in [Todo(s) EcoSystem](https://github.com/corbtastik/todo-ecosystem) actuators, Discovery Client and Config Client are baked into the app.

#### Check root API

```bash
> http :8002/
HTTP/1.1 200  
Content-Type: application/hal+json;charset=UTF-8

{
    "_links": {
        "profile": {
            "href": "http://localhost:8002/profile"
        },
        "todos": {
            "href": "http://localhost:8002/todos{?page,size,sort}",
            "templated": true
        }
    }
}
```

#### Spring Data Rest CRUD ops

##### Create  

```bash
> http :8002/todos title="make bacon pancakes"
HTTP/1.1 201  
Content-Type: application/json;charset=UTF-8
Location: http://localhost:8002/todos/55

{
    "_links": {
        "self": {
            "href": "http://localhost:8002/todos/55"
        },
        "todo": {
            "href": "http://localhost:8002/todos/55"
        }
    },
    "completed": false,
    "id": 55,
    "title": "make bacon pancakes"
}
```

### References

See this issue when deploying to cf
https://github.com/spring-guides/gs-messaging-redis/issues/3