package com.quick.start.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
 * Example for testing repository. We must test all CRUD operations and custom
 * ones
 */
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/datasets/ExampleDataSet.xml" })
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/datasets/ExampleDataSet.xml" })
public class ExampleRepTest extends AbstractTest {

    @Autowired
    private ExampleRepository exampleRepository;

    /**
     * Tests for custom methods
     */

    @Test
    public void testSearchExamples() {
        // fetching all of examples with 'Test' in the name
        Page<Example> examples = this.exampleRepository.searchExamples("Test", null);

        // verifying that it has all 3 registries added on database setup
        assertEquals(3, examples.getContent().size());
        for (Example e : examples.getContent()) {
            System.out.printf("Id: %d Name: %s\n", e.getId(), e.getName());
        }

        // Trying with string '2'
        examples = this.exampleRepository.searchExamples("2", null);

        // Verifying that it has only 1 registry searched
        assertEquals(1, examples.getContent().size());
        for (Example e : examples.getContent()) {
            System.out.printf("Id: %d Name: %s\n", e.getId(), e.getName());
        }
    }

    /**
     * CRUD tests, its meaning are on the name of the tests
     */

    @Test
    public void testGetExample(){
        //Fetching example that doesn't exist
        Example example = this.exampleRepository.findOne(2L);
        assertNull(example);

        //Fetching example that exists
        example = this.exampleRepository.findOne(50L);
        assertNotNull(example);
        assertEquals("Test", example.getName());
    }

    @Test
    public void testInsertExample() {
        Example example = new Example();

        // Filling example instance
        example = new Example();
        example.setName("Test 4");
        example = this.exampleRepository.save(example);

        // Fetching to see if it saved
        Example exampleSaved = this.exampleRepository.findOne(example.getId());
        assertNotNull(exampleSaved);
        assertEquals("Test 4", exampleSaved.getName());
    }

    @Test
    public void testUpdateExample() {
        // Fetching one example to update
        Example example = this.exampleRepository.findOne(51L);
        example.setName("Test 5");

        // Saving it
        example = this.exampleRepository.save(example);

        // Fetching to see if it changed
        Example exampleUpdated = this.exampleRepository.findOne(example.getId());
        assertNotNull(exampleUpdated);
        assertEquals("Test 5", exampleUpdated.getName());
    }

    @Test
    public void testRemoveExample(){
        //Trying to remove one
        this.exampleRepository.delete(50L);

        //Fetching to see if it was removed
        Example example = this.exampleRepository.findOne(50L);
        assertNull(example);
    }

}