package co.com.icesi.Eshop.dto.security;

import lombok.Builder;

@Builder
public record JwtDTO(String token , String role) {
}
