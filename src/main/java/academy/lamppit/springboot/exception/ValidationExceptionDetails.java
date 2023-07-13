package academy.lamppit.springboot.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{
    private final String field;
    private final String fieldMessage;
}
