package io.corbs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoDeletedEvent {
    private Integer id;
    public TodoDeletedEvent() {
        this.id = null;
    }
    public TodoDeletedEvent(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
}
