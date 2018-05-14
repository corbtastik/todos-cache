package io.corbs;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("todos")
public interface TodosRepo extends PagingAndSortingRepository<Todo, Integer> {
    List<Todo> findByTitle(String title);
}
