package io.github.maeloliveira.rest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Age is required")
    private Integer age;
}
