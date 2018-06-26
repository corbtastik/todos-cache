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

### Run on PAS

[Pivotal Application Service](https://pivotal.io/platform/pivotal-application-service) is a modern runtime for Java, .NET, Node.js apps and many more, that provides a connected 5-star development to delivery experience.  PAS provides a cloud agnostic surface for delivering apps, apps such as Spring Boot Microservices.  Rarely in computing do we see this level of harmony between an application development framework and a platform.  Its supersonic dev to delivery with only Cloud Native principles as the interface :sunglasses:

#### manifest.yml & vars.yml

The only PAS specific artifacts in this code repo are ``manifest.yml`` and ``vars.yml``.  Modify ``vars.yml`` to add properties **specific to your PAS environment**. See [Variable Substitution](https://docs.cloudfoundry.org/devguide/deploy-apps/manifest.html#multi-manifests) for more information.  The gist is we only need to set values for our PAS deployment in ``vars.yml`` and pass that file to ``cf push``.

The Todo(s) Cache requires 2 environment variables:

1. ``EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE`` - Service Discovery URL
2. ``SPRING_CLOUD_CONFIG_URI`` - Spring Cloud Config Server URL

and 2 services:

1. todos-cache - Redis backing cache
2. todos-messaging - RabbitMQ messaging backbone

#### manifest.yml

```yml
---
applications:
- name: ((app.name))
  memory: ((app.memory))
  routes:
  - route: ((app.route))
  path: ((app.artifact))
  buildpack: java_buildpack
  env:
    ((env-key-1)): ((env-val-1))
    ((env-key-2)): ((env-val-2))
  services:
   - ((srv-key-1))
   - ((srv-key-2))
```  

#### vars.yml

```yml
app:
  name: todos-cache
  artifact: target/todos-cache-1.0.0.SNAP.jar
  memory: 1G
  route: todos-cache.cfapps.io
env-key-1: EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE
env-val-1: http://cloud-index.cfapps.io/eureka/
env-key-2: SPRING_CLOUD_CONFIG_URI
env-val-2: http://config-srv.cfapps.io
srv-key-1: todos-cache
srv-key-2: todos-messaging
```

# cf push...awe yeah  

Yes you can go from zero to hero with one command :)

Make sure you're in the Todo(s) Cache project root (folder with ``manifest.yml``) and cf push...awe yeah!

```bash
> cf push --vars-file ./vars.yml
```

```bash
> cf apps
Getting apps in org bubbles / space dev as ...
OK

name            requested state   instances   memory   disk   urls
todos-cache     started           1/1         1G       1G     todos-cache.cfapps.io
```

### References  

See this issue when deploying to cf
https://github.com/spring-guides/gs-messaging-redis/issues/3