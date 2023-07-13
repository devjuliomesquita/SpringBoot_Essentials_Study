package academy.lamppit.springboot.mapper;

import academy.lamppit.springboot.domain.Anime;
import academy.lamppit.springboot.requests.AnimePostRequest;
import academy.lamppit.springboot.requests.AnimePutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
    public abstract Anime toAnime(AnimePostRequest animePostRequest);
    public abstract  Anime toAnime(AnimePutRequest animePutRequest);
}
