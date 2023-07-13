package academy.lamppit.springboot.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnimePostRequest {
    @NotEmpty(message = "this field name cannot be empty or null.")
    private String name;
}
