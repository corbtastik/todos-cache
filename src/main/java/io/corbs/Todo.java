package io.corbs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("todos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Todo implements Serializable {
    @Id
    private Integer id;
    private String title = "";
    private Boolean completed = false;
}
