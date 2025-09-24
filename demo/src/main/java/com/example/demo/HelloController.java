package com.example.demo;

//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

//@RestController
//public class HelloController {
//
//    @GetMapping("/")
//    public String index() {
//        return "Hello, World!";
//    }
//
//    @GetMapping("/health")
//    public String health() {
//        return "OK";
//    }
//}

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/ping")
    public String ping() { return "pong"; }
}