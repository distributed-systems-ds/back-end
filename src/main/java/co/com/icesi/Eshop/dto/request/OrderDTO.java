package co.com.icesi.Eshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private String orderId;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String status;

    @Min(value = 0)
    private Long total;


    @NotNull
    private List<String> items;

}
