package com.elkamil.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloworld")
public class HelloWorld {

    @GetMapping
    public ResponseEntity<String> helloworld() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
