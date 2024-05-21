package co.com.icesi.Eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    @NotNull
    private String id;
    @NotBlank
    @NotEmpty
    @NotNull
    private String userEmail;
    @NotBlank
    @NotEmpty
    @NotNull
    private String status;
    @NotBlank
    @NotEmpty
    @NotNull
    private Long total;
    @NotBlank
    @NotEmpty
    @NotNull
    private List<String> items;
}
