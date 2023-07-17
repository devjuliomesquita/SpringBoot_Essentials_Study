package academy.lamppit.springboot.service;

import academy.lamppit.springboot.controller.AnimeController;
import academy.lamppit.springboot.domain.Anime;
import academy.lamppit.springboot.repository.AnimeRepository;
import academy.lamppit.springboot.requests.AnimePostRequest;
import academy.lamppit.springboot.requests.AnimePutRequest;
import academy.lamppit.springboot.util.AnimeCreator;
import academy.lamppit.springboot.util.AnimePostRequestBodyCreator;
import academy.lamppit.springboot.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Anime Service")
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepositoryMock;
    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);
        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createAnimeValidAnime());
        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }
    @Test
    @DisplayName("List returns list of Anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));
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
        List<Anime> animeList = animeService.listAllNoPageable();
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
        Anime anime = animeService.findById(1);
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }
    @Test
    @DisplayName("Find by Name return Anime when successful")
    void findByName_ReturnAnimeByName_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();
        List<Anime> animeList = animeService.findByName("teste");
        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("Create Anime when successful")
    void create_ReturnAnime_WhenSuccessful(){
        Anime animeCreate = animeService.create(AnimePostRequestBodyCreator.createAnimePostRequestBody());
        Assertions.assertThat(animeCreate)
                .isNotNull()
                .isEqualTo(AnimeCreator.createAnimeValidAnime());
    }
    @Test
    @DisplayName("Update Anime when successful")
    void update_AnimeUpdate_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeService.update(AnimePutRequestBodyCreator.putAnimeRequestCreator()))
                .doesNotThrowAnyException();

    }
    @Test
    @DisplayName("Delete Anime when successful")
    void delete_AnimeDelete_WhenSuccessful(){
        Assertions.assertThatCode(()-> animeService.delete(1L))
                .doesNotThrowAnyException();

    }

}