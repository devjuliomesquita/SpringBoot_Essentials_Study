package academy.lamppit.springboot.controller;

import academy.lamppit.springboot.domain.Anime;
import academy.lamppit.springboot.requests.AnimePostRequest;
import academy.lamppit.springboot.requests.AnimePutRequest;
import academy.lamppit.springboot.service.AnimeService;
import academy.lamppit.springboot.util.AnimeCreator;
import academy.lamppit.springboot.util.AnimePostRequestBodyCreator;
import academy.lamppit.springboot.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Anime Controller")
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;
    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);
        BDDMockito.when(animeServiceMock.listAllNoPageable())
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createAnimeValidAnime());
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeServiceMock.create(ArgumentMatchers.any(AnimePostRequest.class)))
                .thenReturn(AnimeCreator.createAnimeValidAnime());
        BDDMockito.doNothing().when(animeServiceMock).update(ArgumentMatchers.any(AnimePutRequest.class));
        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }
    @Test
    @DisplayName("List returns list of Anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("List returns list of Anime when successful")
    void list_ReturnsListOfAnime_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();
        List<Anime> animeList = animeController.list().getBody();
        Assertions.assertThat(animeList)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("Find by Id return Anime when successful")
    void findById_ReturnAnimeById_WhenSuccessful(){
        Long expectedId = AnimeCreator.createAnimeValidAnime().getId();
        Anime anime = animeController.findById(1).getBody();
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }
    @Test
    @DisplayName("Find by Name return Anime when successful")
    void findByName_ReturnAnimeByName_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();
        List<Anime> animeList = animeController.findByName("teste").getBody();
        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("Create Anime when successful")
    void create_ReturnAnime_WhenSuccessful(){
        Anime animeCreate = animeController.create(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
        Assertions.assertThat(animeCreate)
                .isNotNull()
                .isEqualTo(AnimeCreator.createAnimeValidAnime());
    }
    @Test
    @DisplayName("Update Anime when successful")
    void update_AnimeUpdate_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeController.update(AnimePutRequestBodyCreator.putAnimeRequestCreator()))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.update((AnimePutRequestBodyCreator.putAnimeRequestCreator()));
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    @Test
    @DisplayName("Delete Anime when successful")
    void delete_AnimeDelete_WhenSuccessful(){
        Assertions.assertThatCode(()-> animeController.delete(1L))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.delete(1L);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}