package academy.lamppit.springboot.util;

import academy.lamppit.springboot.requests.AnimePostRequest;
import academy.lamppit.springboot.util.AnimeCreator;

public class AnimePostRequestBodyCreator {
    public static AnimePostRequest createAnimePostRequestBody(){
        return AnimePostRequest.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();

    }
}
