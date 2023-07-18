package academy.lamppit.springboot.integration;

import academy.lamppit.springboot.domain.Anime;
import academy.lamppit.springboot.repository.AnimeRepository;
import academy.lamppit.springboot.util.AnimeCreator;
import academy.lamppit.springboot.util.AnimePostRequestBodyCreator;
import academy.lamppit.springboot.util.AnimePutRequestBodyCreator;
import academy.lamppit.springboot.wrapper.PageableResponse;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

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
//    @DisplayName("Create Anime when successful")
//    void create_ReturnAnime_WhenSuccessful(){
//        Anime animeCreate = animeController.create(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
//        Assertions.assertThat(animeCreate)
//                .isNotNull()
//                .isEqualTo(AnimeCreator.createAnimeValidAnime());
//    }
//    @Test
//    @DisplayName("Update Anime when successful")
//    void update_AnimeUpdate_WhenSuccessful(){
//        Assertions.assertThatCode(() -> animeController.update(AnimePutRequestBodyCreator.putAnimeRequestCreator()))
//                .doesNotThrowAnyException();
//        ResponseEntity<Void> entity = animeController.update((AnimePutRequestBodyCreator.putAnimeRequestCreator()));
//        Assertions.assertThat(entity).isNotNull();
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }
//    @Test
//    @DisplayName("Delete Anime when successful")
//    void delete_AnimeDelete_WhenSuccessful(){
//        Assertions.assertThatCode(()-> animeController.delete(1L))
//                .doesNotThrowAnyException();
//        ResponseEntity<Void> entity = animeController.delete(1L);
//        Assertions.assertThat(entity).isNotNull();
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }
}
