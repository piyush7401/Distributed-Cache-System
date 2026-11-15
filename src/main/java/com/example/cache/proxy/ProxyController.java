package main.java.com.example.cache.proxy;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Profile("proxy")
public class ProxyController {

    private RestClientService restClientService;

    public ProxyController(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        return restClientService.getFromServer(key);
    }

    @PostMapping("/put")
    public String putValue(@RequestParam String key, @RequestParam String value) {
        return restClientService.putToServer(key, value);
    }

}
