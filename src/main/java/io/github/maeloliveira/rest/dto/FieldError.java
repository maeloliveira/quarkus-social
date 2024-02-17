package io.github.maeloliveira.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldError {

    private String field;
    private String message;

    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
