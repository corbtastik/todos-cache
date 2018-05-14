package io.corbs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TodoUpdatedEvent {
    private Todo todo;
    public TodoUpdatedEvent(Todo todo) {
        this.todo = todo;
    }
}
