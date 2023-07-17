package academy.lamppit.springboot.requests;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimePostRequest {
    @NotEmpty(message = "this field name cannot be empty or null.")
    private String name;
}
