package io.corbs;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TodosRepo extends PagingAndSortingRepository<Todo, Integer> {
    List<Todo> findByTitle(String title);
}
