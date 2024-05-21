package co.com.icesi.Eshop.dto.security;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record LoginDTO(
        @NotBlank
        String username,

        @NotBlank
        String password

) {
}
