package academy.lamppit.springboot.integration;

import academy.lamppit.springboot.domain.Anime;
import academy.lamppit.springboot.repository.AnimeRepository;
import academy.lamppit.springboot.requests.AnimePostRequest;
import academy.lamppit.springboot.util.AnimeCreator;
import academy.lamppit.springboot.util.AnimePostRequestBodyCreator;
import academy.lamppit.springboot.util.AnimePutRequestBodyCreator;
import academy.lamppit.springboot.wrapper.PageableResponse;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private AnimeRepository animeRepository;
    @LocalServerPort
    private int port;
    @Test
    @DisplayName("List returns list of Anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful(){
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedAnimeName = animeSaved.getName();
        PageableResponse<Anime> animePage = testRestTemplate.exchange("/anime", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedAnimeName);
    }
    @Test
    @DisplayName("List returns list of Anime when successful")
    void list_ReturnsListOfAnime_WhenSuccessful(){
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedAnimeName = animeSaved.getName();
        List<Anime> animeList = testRestTemplate.exchange("/anime/listAll", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();
        Assertions.assertThat(animeList)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedAnimeName);
    }
    @Test
    @DisplayName("Find by Id return Anime when successful")
    void findById_ReturnAnimeById_WhenSuccessful(){
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long expectedId = animeSaved.getId();
        Anime anime = testRestTemplate.getForObject("/anime/{id}", Anime.class, expectedId);
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("Find by Name return Anime when successful")
    void findByName_ReturnAnimeByName_WhenSuccessful(){
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedAnimeName = animeSaved.getName();
        String url = String.format("/anime/find?name=%s", expectedAnimeName);
        List<Anime> animeList = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();
        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedAnimeName);
    }

//    @Test
//    @DisplayName("save returns anime when successful")
//    void save_ReturnsAnime_WhenSuccessful() {
//        AnimePostRequest animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
//
//        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);
//        log.info(animeResponseEntity.getStatusCode());
//        Assertions.assertThat(animeResponseEntity).isNotNull();
//        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
//        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
//    }
    @Test
    @DisplayName("Update Anime when successful")
    void update_AnimeUpdate_WhenSuccessful(){
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        animeSaved.setName("Hijime-no-ippo 3° temporada");
        ResponseEntity<Void> entity = testRestTemplate.exchange("/anime", HttpMethod.PUT, new HttpEntity<>(animeSaved), Void.class);
        log.info(entity.getStatusCode());log.info(entity);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    @Test
    @DisplayName("Delete Anime when successful")
    void delete_AnimeDelete_WhenSuccessful(){
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> entity = testRestTemplate.exchange("/anime/{id}", HttpMethod.DELETE, null, Void.class, animeSaved.getId());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
