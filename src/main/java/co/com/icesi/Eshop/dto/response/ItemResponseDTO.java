package co.com.icesi.Eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDTO {
    @NotBlank
    @NotEmpty
    @NotNull
    private String description;
    @NotBlank
    @NotEmpty
    @NotNull
    private String name;
    @NotBlank
    @NotEmpty
    @NotNull
    private Long price;
    @NotBlank
    @NotEmpty
    @NotNull
    private String imageUrl;
    @NotBlank
    @NotEmpty
    @NotNull
    private String category;
    @NotBlank
    @NotEmpty
    @NotNull
    private String orderId;
}
