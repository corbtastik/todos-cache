package io.corbs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TodoCreatedEvent {
    private Todo todo;
    public TodoCreatedEvent(Todo todo) {
        this.todo = todo;
    }
}
