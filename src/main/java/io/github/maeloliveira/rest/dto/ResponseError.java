package io.github.maeloliveira.rest.dto;

import jakarta.validation.ConstraintViolation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
public class ResponseError {

    private String message;
    private Collection<FieldError> errors;

    public ResponseError(String message, List<FieldError> errors) {
    this.message = message;
    this.errors  = errors;
    }

    public static <T> ResponseError crateFromValidation(
            Set<ConstraintViolation<T>> violations) {
        List<FieldError> errors = violations
                .stream().map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        String message = "Validation Error";

        return new ResponseError(message, errors);
    }
}
