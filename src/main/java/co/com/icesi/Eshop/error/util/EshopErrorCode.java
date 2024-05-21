package co.com.icesi.Eshop.error.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EshopErrorCode {
    NOT_FOUND("The resource you are looking for was not found"),
    MISSING_FIELDS("The fields %s are missing"),
    BAD_CREDENTIALS("Please check your email or password"),
    RUNTIME("We run into an error, please check de logs");
    private final String message;
}
