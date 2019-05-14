package com.quick.start.domain.repositories;

import com.quick.start.domain.entities.Example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Example interface for Repository
 */
public interface ExampleRepository extends JpaRepository<Example, Long> {

    /**
     * Example repository interface for search with paging
     * 
     * @param name     name to be searched
     * @param pageable next page
     * @return Paginated list of examples
     */
    @Query("SELECT e FROM Example e WHERE (:name IS NULL OR e.name LIKE CONCAT('%',:name,'%'))")
    public Page<Example> searchExamples(@Param("name") String name, Pageable pageable);

}