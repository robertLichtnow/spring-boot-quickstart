package com.quick.start.domain.services;

import com.quick.start.domain.entities.Example;
import com.quick.start.domain.repositories.ExampleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Example service
 */
@Service
public class ExampleService {

    @Autowired
    private ExampleRepository exampleRepository;

    /**
     * Method for searching examples
     * @param name the name to be searched
     * @param pageable the pageable object for pagination
     * @return the page containing the examples filtered by the name
     */
    public Page<Example> searchExample(String name, Pageable pageable) {
        return this.exampleRepository.searchExamples(name, pageable);
    }

}