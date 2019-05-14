package com.quick.start.repositories;

import static org.junit.Assert.assertEquals;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.quick.start.AbstractTest;
import com.quick.start.domain.entities.Example;
import com.quick.start.domain.repositories.ExampleRepository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

/**
 * Example for testing repository. We must test all CRUD operations and custom ones
 */
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {
    "/datasets/ExampleDataSet.xml"
})
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = {
    "/datasets/ExampleDataSet.xml"
})
public class ExampleRepTest extends AbstractTest{

    @Autowired
    private ExampleRepository exampleRepository;

    @Test
    public void testSearchExamples(){
        //fetching all of examples with 'Test' in the name
        Page<Example> examples = this.exampleRepository.searchExamples("Test", null);
        
        //verifying that it has all 3 registries added on database setup
        assertEquals(3, examples.getContent().size());
        for(Example e : examples.getContent()){
            System.out.printf("Id: %d Name: %s\n", e.getId(), e.getName());
        }

        //Trying with string '2'
        examples = this.exampleRepository.searchExamples("2", null);

        //Verifying that it has only 1 registry searched
        assertEquals(1, examples.getContent().size());
        for(Example e : examples.getContent()){
            System.out.printf("Id: %d Name: %s\n", e.getId(), e.getName());
        }
    }

    /**
     * Add CRUD tests next
     */
    
}