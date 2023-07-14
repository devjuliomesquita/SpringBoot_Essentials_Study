package academy.lamppit.springboot.service;

import academy.lamppit.springboot.domain.Anime;
import academy.lamppit.springboot.exception.BadRequestException;
import academy.lamppit.springboot.mapper.AnimeMapper;
import academy.lamppit.springboot.repository.AnimeRepository;
import academy.lamppit.springboot.requests.AnimePostRequest;
import academy.lamppit.springboot.requests.AnimePutRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final AnimeMapper animeMapper;


    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public Anime findById(long id) {
        return animeRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException("Anime not found."));
    }
    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime create(AnimePostRequest animePostRequest) {
        Anime anime = animeMapper.toAnime(animePostRequest);
        return animeRepository.save(anime);

    }
    public void update(AnimePutRequest animePutRequest){
        Anime animeSaved = findById(animePutRequest.getId());
        Anime anime = animeMapper.toAnime(animePutRequest);
        anime.setId(animeSaved.getId());
        animeRepository.save(anime);

    }

    public void delete(long id) {
        animeRepository.delete(findById(id));
    }

}
