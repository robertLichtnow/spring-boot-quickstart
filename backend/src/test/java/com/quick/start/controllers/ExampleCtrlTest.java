package com.quick.start.controllers;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.quick.start.AbstractControllerTest;
import com.quick.start.domain.entities.Example;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

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

    @Test
    public void testGetExample() throws Exception{
        // Fetching one the doesn't exist
        this.mockMvc.perform(get("/api/example/{id}", 10))
            .andDo(print())
            .andExpect(status().isNotFound());

        // Fetching one that exists
        this.mockMvc.perform(get("/api/example/{id}", 50))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name", equalTo("Test")));
    }

    @Test
    public void testCreateExample() throws Exception{
        Example example = new Example();

        // Tries to add an empty one
        this.mockMvc.perform(post("/api/example")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertToJson(example)))
            .andDo(print())
            .andExpect(status().isBadRequest());

        // Fill the object and tries to add
        example.setName("Test 4");
        this.mockMvc.perform(post("/api/example")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertToJson(example)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Test 4")));
    }

    @Test
    public void testUpdateExample() throws Exception{
        // Fetch one that exists
        MvcResult result = this.mockMvc.perform(get("/api/example/{id}",50))
            .andExpect(status().is2xxSuccessful())
            .andReturn();

        // Parses it
        Example example = convertFromJson(result.getResponse().getContentAsString(), Example.class);
        
        // Change name value
        example.setName("Test 27");

        // Updates it and checks if it was updated
        this.mockMvc.perform(put("/api/example")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertToJson(example)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Test 27")));

    }
    
}