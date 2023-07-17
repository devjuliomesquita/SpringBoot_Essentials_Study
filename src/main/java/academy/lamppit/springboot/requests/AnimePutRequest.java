package academy.lamppit.springboot.requests;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AnimePutRequest {
    @NotEmpty(message = "this field id cannot be empty or null.")
    private Long id;
    @NotEmpty(message = "this field name cannot be empty or null.")
    private String name;
}
