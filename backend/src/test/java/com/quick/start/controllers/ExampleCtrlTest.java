package com.quick.start.controllers;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.quick.start.AbstractControllerTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;

/**
 * Example for test of controllers
 */
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {
    "/datasets/ExampleDataSet.xml"
})
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = {
    "/datasets/ExampleDataSet.xml"
})
public class ExampleCtrlTest extends AbstractControllerTest{

    @Test
    public void testSearchExamples() throws Exception{
        //Fetching all of the registries
        this.mockMvc.perform(get("/api/example/search").param("name", ""))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(3)));
        
        //fetching registries with '2' on the name
        this.mockMvc.perform(get("/api/example/search").param("name", "2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)));
    }
    
}