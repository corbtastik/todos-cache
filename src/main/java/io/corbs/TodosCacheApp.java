package io.corbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisRepositories
@RestController
@EnableBinding(TodosCacheApp.SinkChannels.class)
public class TodosCacheApp implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TodosCacheApp.class);

    private final TodosRepo repo;

    interface SinkChannels {
        @Input
        SubscribableChannel todoCreatedEvent();
        @Input
        SubscribableChannel todoUpdatedEvent();
        @Input
        SubscribableChannel todoDeletedEvent();
    }

    @Autowired
    public TodosCacheApp(TodosRepo repo) {
        this.repo = repo;
    }

    @StreamListener("todoCreatedEvent")
    void onCreatedEvent(CreatedEvent event) {
        if(ObjectUtils.isEmpty(event.getTodo().getId())) {
            return;
        }
        LOG.debug("caching todo " + event.getTodo());
        this.repo.save(event.getTodo());
    }

    @StreamListener("todoUpdatedEvent")
    void onUpdatedEvent(UpdatedEvent event) {
        if(ObjectUtils.isEmpty(event.getTodo().getId())) {
            return;
        }
        LOG.debug("updating todo " + event.getTodo().toString());
        this.repo.save(event.getTodo());
    }

    @StreamListener("todoDeletedEvent")
    void onDeletedEvent(DeletedEvent event) {
        if(!ObjectUtils.isEmpty(event.getId())) {
            LOG.debug("removing todo " + event.getId());
            this.repo.deleteById(event.getId());
        } else {
            LOG.debug("removing all todo(s)");
            this.repo.deleteAll();
        }
    }

    @Override
    public void run(String... args) {
        Todo todo1 = Todo.builder().id(-1).title("todo test 1").build();
        Todo todo2 = Todo.builder().id(-2).title("todo test 2").build();
        Todo todo3 = Todo.builder().id(-3).title("todo test 3").build();

        this.repo.save(todo1);
        this.repo.save(todo2);
        this.repo.save(todo3);
    }

    public static void main(String[] args) {
        SpringApplication.run(TodosCacheApp.class, args);
    }
}

