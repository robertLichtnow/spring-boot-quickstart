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
public class ExampleService extends AbstractService<Example>{

    @Autowired
    private ExampleRepository exampleRepository;

    /**
     * Searches examples based on the parameters passed
     * @param name the name to be searched
     * @param pageable the pageable object for pagination
     * @return the page containing the examples filtered by the name
     */
    public Page<Example> searchExample(String name, Pageable pageable) {
        return this.exampleRepository.searchExamples(name, pageable);
    }

    /**
     * Find the example with the provided id
     * @param id the provided id
     * @return the example with the provided id or <code>null</code>
     */
    public Example getExample(Long id){
        return this.exampleRepository.findOne(id);
    }

    /**
     * Saves the example provided
     * @param example the example provided
     * @return the saved example
     */
    public Example createExample(Example example){        
        // Validates the object. May throw exception if not validated
        this.validate(example);

        // Clears id for avoiding overriding existing registries
        example.setId(null);

        // Saves and returns it
        return this.exampleRepository.save(example);
    }

    /**
     * Updates the example provided
     * @param example the example provided
     * @return the updated example
     */
    public Example updateExample(Example example){
        // Validates the object. May throw exception if not validated
        this.validate(example);
        
        // Verify if it exists in database
        Example oldExample = this.exampleRepository.findOne(example.getId());
        if(oldExample == null)
            // Creates a new example if it doesn't exist in database
            example.setId(null);
        
        // Saves it on database
        return this.exampleRepository.save(example);
    }
    
    /**
     * Removes the example with the provided id
     * @param id the provided id
     * @return <code>true</code> if the example was removed, <code>false</code> otherwise.
     */
    public boolean removeExample(Long id){
        // Checks if there is a registry with the providaded id
        Example example = this.getExample(id);
        if(example == null)
            return false;
        
        // Removes the registry
        this.exampleRepository.delete(id);
        return true;
    }
    
 

}