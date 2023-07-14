package academy.lamppit.springboot.client;

import academy.lamppit.springboot.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String... args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/anime/{id}", Anime.class, 1);
        log.info(entity);
        Anime object = new RestTemplate().getForObject("http://localhost:8080/anime/{id}", Anime.class, 1);
        log.info(object);
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/anime/listAll", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
        });
        log.info(exchange.getBody());
    }
}
