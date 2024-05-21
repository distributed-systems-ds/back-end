package co.com.icesi.Eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityResponseDTO {
    @NotNull
    private String authority;
    @NotNull
    private String role;
}
