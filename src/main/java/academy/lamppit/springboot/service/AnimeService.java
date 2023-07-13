package academy.lamppit.springboot.service;

import academy.lamppit.springboot.domain.Anime;
import academy.lamppit.springboot.exception.BadRequestException;
import academy.lamppit.springboot.mapper.AnimeMapper;
import academy.lamppit.springboot.repository.AnimeRepository;
import academy.lamppit.springboot.requests.AnimePostRequest;
import academy.lamppit.springboot.requests.AnimePutRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;


    public List<Anime> listAll() {
        return animeRepository.findAll();
    }

    public Anime findById(long id) {
        return animeRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException("Anime not found."));
    }
    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime create(AnimePostRequest animePostRequest) {
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequest);
        return animeRepository.save(anime);

    }
    public void update(AnimePutRequest animePutRequest){
        Anime animeSaved = findById(animePutRequest.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequest);
        anime.setId(animeSaved.getId());
        animeRepository.save(anime);

    }

    public void delete(long id) {
        animeRepository.delete(findById(id));
    }

}
