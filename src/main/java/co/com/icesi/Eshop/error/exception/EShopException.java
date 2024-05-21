package co.com.icesi.Eshop.error.exception;

import lombok.Getter;


@Getter
public class EShopException extends RuntimeException{
    private final EShopError eShopError;
    public  EShopException(String message, EShopError eShopError){
        super(message);
        this.eShopError = eShopError;
    }
}
