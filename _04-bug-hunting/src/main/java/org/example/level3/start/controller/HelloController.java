package org.example.level3.start.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
        // Bug: Potential XSS vulnerability
        return "<html><body><h1>Hello, " + name + "!</h1></body></html>";
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        // Bug: Potential performance issue
        Thread.sleep(5000);  // Simulates slow processing
        return "This is a slow endpoint";
    }

    @GetMapping("/data")
    public String data(@RequestParam(name = "id", defaultValue = "1") String id) {
        // Bug: No validation of input
        int intId = Integer.parseInt(id);  // Potential NumberFormatException
        return "Data for ID: " + intId;
    }
}
