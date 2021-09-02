package com.SpringSecurityOAuth2JWT.springbootoauth2jwt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/home")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
