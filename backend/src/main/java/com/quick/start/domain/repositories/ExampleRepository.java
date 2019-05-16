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

    @Query("SELECT e FROM Example e WHERE (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%',:name,'%')))")
    public Page<Example> searchExamples(@Param("name") String name, Pageable pageable);

}