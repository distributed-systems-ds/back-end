package co.com.icesi.Eshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {


    @NotBlank
    private String description;
    @NotBlank
    private String name;

    @Min(value = 0)
    private Long price;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String category;

    private String orderId;

}
