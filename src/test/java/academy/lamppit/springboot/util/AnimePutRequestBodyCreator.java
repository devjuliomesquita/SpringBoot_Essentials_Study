package academy.lamppit.springboot.util;

import academy.lamppit.springboot.requests.AnimePutRequest;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequest putAnimeRequestCreator(){
        return AnimePutRequest.builder()
                .name(AnimeCreator.createAnimeValidUpdateAnime().getName())
                .id(AnimeCreator.createAnimeValidUpdateAnime().getId())
                .build();
    }
}
