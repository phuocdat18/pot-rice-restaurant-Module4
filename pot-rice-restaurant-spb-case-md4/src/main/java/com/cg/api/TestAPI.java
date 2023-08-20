package com.cg.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestAPI {

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<String> str = List.of(
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1",
                "your content 1"

        );


        return new ResponseEntity<>(str, HttpStatus.OK);
    }
}
