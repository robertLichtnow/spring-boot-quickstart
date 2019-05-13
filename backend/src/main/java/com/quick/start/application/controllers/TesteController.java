package com.quick.start.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TesteController
 */
@RestController
public class TesteController {

    @RequestMapping("api/teste")
    public ResponseEntity<String> getTeste(){
        return new ResponseEntity<>("Ok",HttpStatus.OK);
    }
}