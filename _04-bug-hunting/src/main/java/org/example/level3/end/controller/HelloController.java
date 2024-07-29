package org.example.level3.end.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
        return "<html><body><h1>Hello, " + HtmlUtils.htmlEscape(name) + "!</h1></body></html>";
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        Thread.sleep(5000);
        return "This is a slow endpoint";
    }

    @GetMapping("/data")
    public String data(@RequestParam(name = "id", defaultValue = "1") String id) {
        try {
            int intId = Integer.parseInt(id);
            return "Data for ID: " + intId;
        } catch (NumberFormatException e) {
            return "Invalid ID format";
        }
    }
}
