package com.quick.start.application.controllers;

import com.quick.start.domain.entities.Example;
import com.quick.start.domain.services.ExampleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @RequestMapping(method = { RequestMethod.GET })
    public Page<Example> searchExample(@RequestParam String name, Pageable pageable) {
        return this.exampleService.searchExample(name, pageable);
    }

}