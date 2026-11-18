package main.java.com.example.cache.proxy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
@Profile("proxy")
public class ConsistentHashing {

//    we want to make Consistent Hashing by 0 to 100 node Circle
    private static final int HASH_SPACE = 100000;
    private static final int VIRTUAL_NODES  = 100;

    private final TreeMap<Integer,String> ring = new TreeMap<>();

    public ConsistentHashing(){
    }

    private int hash(String key) {
        return Math.floorMod(key.hashCode(), HASH_SPACE);
    }

    public synchronized void addServer(String serverName){

        for(int i =0;i<VIRTUAL_NODES ;i++){
            String virtualNode = serverName + "#"+i;
            int position = hash(virtualNode);
            ring.put(position,serverName);
        }
    }

    public synchronized void removeServer(String serverName){
        for(int i =0;i<VIRTUAL_NODES ;i++){
            String virtualNode = serverName + "#"+i;
            int position = hash(virtualNode);
            ring.remove(position);
        }
    }

    public synchronized String routeHashing(String key){

        if(ring.isEmpty()){
            throw new RuntimeException("No servers available");
        }

        int keyHash = hash(key);

        Map.Entry<Integer, String> entry = ring.ceilingEntry(keyHash);

        if(entry != null){
            return entry.getValue();
        }else{
            return ring.firstEntry().getValue();
        }

    }
}
