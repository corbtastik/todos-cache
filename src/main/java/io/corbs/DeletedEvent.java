package io.corbs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class DeletedEvent {
    private Integer id;
    DeletedEvent() {
        this.id = null;
    }
    DeletedEvent(Integer id) {
        this.id = id;
    }
    Integer getId() {
        return id;
    }
}
