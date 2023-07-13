package academy.lamppit.springboot.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnimePutRequest {
    @NotEmpty(message = "this field id cannot be empty or null.")
    private Long id;
    @NotEmpty(message = "this field name cannot be empty or null.")
    private String name;
}
