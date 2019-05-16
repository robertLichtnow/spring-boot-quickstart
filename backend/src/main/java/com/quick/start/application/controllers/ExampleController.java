package com.quick.start.application.controllers;

import com.quick.start.domain.entities.Example;
import com.quick.start.domain.services.ExampleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example for controller
 */
@RestController
@RequestMapping("api/example")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @RequestMapping(path = "/search",method = { RequestMethod.GET })
    public Page<Example> searchExample(@RequestParam String name, Pageable pageable) {
        return this.exampleService.searchExample(name, pageable);
    }

    @RequestMapping(path = "/{id}", method = { RequestMethod.GET })
    public ResponseEntity<Example> getExample(@PathVariable Long id){
        Example example = this.exampleService.getExample(id);
        if(example == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Example>(example, HttpStatus.OK);
    }

    @RequestMapping(method = { RequestMethod.POST })
    public Example createExample(@RequestBody Example example){
        return this.exampleService.createExample(example);
    }

    @RequestMapping(method = { RequestMethod.PUT })
    public Example updateExample(@RequestBody Example example){
        return this.exampleService.updateExample(example);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(path = "/{id}",method = { RequestMethod.DELETE })
    public ResponseEntity removeExample(@PathVariable Long id){
        if(!this.exampleService.removeExample(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}