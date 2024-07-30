package org.example.level3.start.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
        return "<html><body><h1>Hello, " + name + "!</h1></body></html>";
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        performSlowOperation();  // Simulate slowness with a non-trivial computation
        return "This is a slow endpoint";
    }

    @GetMapping("/data")
    public String data(@RequestParam(name = "id", defaultValue = "1") String id) {
        int intId = Integer.parseInt(id);
        return "Data for ID: " + intId;
    }

    private void performSlowOperation() {
        long endTime = System.currentTimeMillis() + 5000; // Run the loop for 10 seconds
        while (System.currentTimeMillis() < endTime) {
            // Perform some computation to keep the CPU busy
            double value = Math.sqrt(Math.random()) * Math.pow(Math.random(), Math.random());
        }
    }
}
