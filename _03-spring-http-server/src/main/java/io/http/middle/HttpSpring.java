package io.http.middle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// TODO: 1. add a few annotations here
public class HttpSpring {

    private Map<Integer, String> items = new ConcurrentHashMap<>();
    private int currentId = 0;

    public static void main(String[] args) {
        SpringApplication.run(HttpSpring.class, args);
    }

    // TODO: 2. some annotation also here
    public Map<Integer, String> getItems() {
        return items;
    }

    // TODO: 3. very similar annotation with slight modification
    public String getItem(@PathVariable int id) {
        return items.getOrDefault(id, "Item not found");
    }

    // TODO: 4. another annotation
    public String createItem(@RequestBody String item) {
        items.put(++currentId, item);
        return "Item: " + item.toString() + " with ID has been created!";
    }

    // TODO: 5. another annotation problem
    public String deleteItem(@PathVariable("id") int id) {
        if (items.remove(id) != null) {
            return "Item has been removed";
        } else {
            return "Item not found!";
        }
    }
}
