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
        String url = proxyServer.routeRequest(key);
        try{
            return restClient.get()
                    .uri(url+ "/api/get?key={key}", key)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching data from server", e);
        }
    }

    public String putToServer(String key,String value){
        String url = proxyServer.routeRequest(key);

        try{
            return restClient.post()
                    .uri(url+ "/api/put?key={key}&value={value}", key, value)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while putting data to server", e);
        }
    }


}
