package co.com.icesi.Eshop.error.exception;

import co.com.icesi.Eshop.error.util.EshopErrorCode;
import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ErrorDetail {
    private EshopErrorCode errorCode;
    private String errorMessage;
}
