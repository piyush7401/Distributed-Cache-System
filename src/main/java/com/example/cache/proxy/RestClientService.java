package main.java.com.example.cache.proxy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Profile("proxy")
public class RestClientService {

    private ProxyServer proxyServer;
    private RestClient restClient;

    public RestClientService(ProxyServer proxyServer, RestClient restClient) {
        this.proxyServer = proxyServer;
        this.restClient = restClient;
    }

    public String getFromServer(String key){
        int port = proxyServer.routeRequest(key);

        String url = "http://localhost:" + port + "/api/get?key=" + key;
        try{
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching data from server", e);
        }
    }

    public String putToServer(String key,String value){
        int port = proxyServer.routeRequest(key);

        String url = "http://localhost:" + port + "/api/put?key=" + key + "&value=" + value;
        try{
            return restClient.post()
                    .uri(url)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while putting data to server", e);
        }
    }


}
