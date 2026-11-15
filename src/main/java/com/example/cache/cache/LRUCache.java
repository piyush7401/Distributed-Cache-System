package main.java.com.example.cache.cache;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.HashMap;


@Service
@Profile("cache")
public class LRUCache {

    public class Node {
        String key;
        String value;
        Node next;
        Node prev;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    private final HashMap<String, Node> map;
    private final Node head;
    private final Node tail;
    private final int cap;

    public LRUCache() {
        map = new HashMap<>();
        head = new Node("#", "#");
        tail = new Node("#", "#");
        head.next = tail;
        tail.prev = head;
        cap = 10;
    }

    public void addNode(Node temp) {
        Node temper = head.next;
        head.next = temp;
        temp.prev = head;
        temp.next = temper;
        temper.prev = temp;
    }

    public void deleteNode(Node temp){
        Node left = temp.prev;
        Node right = temp.next;
        left.next = right;
        right.prev = left;
    }

    public String get(String key){
        if(map.containsKey(key)){
            Node temp = map.get(key);
            String value = temp.value;
            deleteNode(temp);
            addNode(temp);
            return value;
        }
        return null;
    }

    public void put(String key, String value){
        if(!map.containsKey(key)){
            if(map.size() == cap){
                Node temp = tail.prev;
                deleteNode(temp);
                map.remove(temp.key);
            }
            Node newNode = new Node(key, value);
            addNode(newNode);
            map.put(key, newNode);
        } else {
            Node temp = map.get(key);
            temp.value = value;
            deleteNode(temp);
            addNode(temp);
        }
    }

}
