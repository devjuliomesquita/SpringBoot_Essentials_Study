package academy.lamppit.springboot.client;

import academy.lamppit.springboot.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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
        Anime anime = Anime.builder().name("Samurai X").build();
        Anime animeSaved = new RestTemplate().postForObject("http://localhost:8080/anime", anime, Anime.class);
        log.info(animeSaved);
        Anime anime1 = Anime.builder().name("Trigun").build();
        ResponseEntity<Anime> animeSaved2 = new RestTemplate().exchange("http://localhost:8080/anime", HttpMethod.POST,new HttpEntity<>(anime1), Anime.class);
        log.info(animeSaved2);

        Anime animeSavedUpdate = animeSaved2.getBody();
        animeSavedUpdate.setName("Attack on Titan");
        ResponseEntity<Void> animeUpdate = new RestTemplate().exchange("http://localhost:8080/anime",HttpMethod.PUT, new HttpEntity<>(animeSavedUpdate), Void.class);
        log.info(animeUpdate);
        ResponseEntity<Void> animeDel = new RestTemplate().exchange("http://localhost:8080/anime/{id}", HttpMethod.DELETE, null, Void.class, animeSavedUpdate.getId());
        log.info(animeDel);



    }
}
