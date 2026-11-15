package main.java.com.example.cache.proxy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("proxy")
public class ProxyServer {
    private final Map<Integer,String> serverMap = new HashMap<>();

    public ProxyServer() {
        serverMap.put(8081, "LRU Server 1");
        serverMap.put(8082, "LRU Server 2");
        serverMap.put(8083, "LRU Server 3");
    }

    public int routeRequest(String key) {

        if(serverMap.isEmpty()){
            throw new RuntimeException("No servers available");
        }
        List<Integer> ports = new ArrayList<>(serverMap.keySet());

        Collections.sort(ports);

        int hash = Math.abs(key.hashCode());
        int portIndex = hash % ports.size();
        return ports.get(portIndex);
    }

    public void addServer(int port , String serverName){
        serverMap.put(port,serverName);
    }

    public void removeServer(int port){
        serverMap.remove(port);
    }
}
