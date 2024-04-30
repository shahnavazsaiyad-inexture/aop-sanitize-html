package com.controller;

import com.annotations.SanitizeHtml;
import com.dto.SanitizeType;
import com.dto.TestDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World!";
    }

    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TestDto test(@SanitizeHtml @RequestBody TestDto testDto) {
        return testDto;
    }
}
