package main.java.com.example.cache.proxy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("proxy")
public class ProxyServer {
    private final Map<String,String> serverMap = new HashMap<>();
    private ConsistentHashing consistentHashing;

    public ProxyServer(ConsistentHashing consistentHashing) {
        this.consistentHashing = consistentHashing;
        addServer("server1", "http://localhost:8081");
        addServer("server2", "http://localhost:8082");
        addServer("server3", "http://localhost:8083");
    }

    public String routeRequest(String key) {
        String severId = consistentHashing.routeHashing(key);

        return serverMap.get(severId);
    }

    public void addServer(String serverId , String url){
        if (serverMap.containsKey(serverId)) {
            throw new IllegalArgumentException(
                    "Server already exists: " + serverId
            );
        }

        consistentHashing.addServer(serverId);
        serverMap.put(serverId,url);
    }

    public void removeServer(String serverId){
        if(!serverMap.containsKey(serverId)){
            throw new RuntimeException("Server not exist");
        }

        serverMap.remove(serverId);
        consistentHashing.removeServer(serverId);
    }
}
