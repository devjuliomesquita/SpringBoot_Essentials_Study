package academy.lamppit.springboot.util;

import academy.lamppit.springboot.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
    public static Anime createAnimeValidAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .id(1L)
                .build();
    }
    public static Anime createAnimeValidUpdateAnime(){
        return Anime.builder()
                .name("Hajime no Ippo - Rising")
                .id(1L)
                .build();
    }
}
