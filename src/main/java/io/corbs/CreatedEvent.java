package io.corbs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
class CreatedEvent {
    private Todo todo;
    CreatedEvent(Todo todo) {
        this.todo = todo;
    }
}
