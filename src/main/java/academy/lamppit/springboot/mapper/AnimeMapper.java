package academy.lamppit.springboot.mapper;

import academy.lamppit.springboot.domain.Anime;
import academy.lamppit.springboot.requests.AnimePostRequest;
import academy.lamppit.springboot.requests.AnimePutRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnimeMapper {
    Anime toAnime(AnimePostRequest animePostRequest);
    Anime toAnime(AnimePutRequest animePutRequest);
}
