package io.github.maeloliveira.rest.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {
    @NotBlank(message = "Massage not null")
    private String text;
}

