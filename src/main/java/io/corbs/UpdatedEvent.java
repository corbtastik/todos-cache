package io.corbs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
class UpdatedEvent {
    private Todo todo;
    UpdatedEvent(Todo todo) {
        this.todo = todo;
    }
}
