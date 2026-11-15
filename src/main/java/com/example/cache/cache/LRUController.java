package main.java.com.example.cache.cache;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Profile("cache")
public class LRUController {

    private final LRUCache cache;

    public LRUController(LRUCache cache) {
        this.cache = cache;
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        String value = cache.get(key);
        return value == null ? "Key not found" : value;
    }

    @PostMapping("/put")
    public String putValue(@RequestParam String key, @RequestParam String value) {
        cache.put(key, value);
        return "Value added successfully";
    }
}
