package co.com.icesi.Eshop.error;


import co.com.icesi.Eshop.error.exception.EShopError;
import co.com.icesi.Eshop.error.exception.EShopException;
import co.com.icesi.Eshop.error.util.EshopErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static co.com.icesi.Eshop.error.util.ErrorDetailsManager.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EShopException.class)
    public ResponseEntity<EShopError> handleApplicationException(EShopException exception){
        return ResponseEntity.status(exception.getEShopError().getStatus()).body(exception.getEShopError());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EShopError> handleMissArgumentException(MethodArgumentNotValidException exception){
        EShopError error = EShopError.builder().status(HttpStatus.BAD_REQUEST).details(List.of(sendDetails(createDetail(String.format(EshopErrorCode.MISSING_FIELDS.getMessage(), getMissingFiled(exception)), EshopErrorCode.MISSING_FIELDS)))).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<EShopError> handleAuthException(AuthenticationException exception){
        EShopError error = EShopError.builder().status(HttpStatus.UNAUTHORIZED).details(List.of(sendDetails(createDetail(EshopErrorCode.BAD_CREDENTIALS.getMessage(), EshopErrorCode.BAD_CREDENTIALS)))).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<EShopError> handleRuntimeException(RuntimeException exception){
        EShopError error = EShopError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).details(List.of(sendDetails(createDetail( EshopErrorCode.RUNTIME.getMessage()+"\n possible reason: "+exception.getMessage(), EshopErrorCode.RUNTIME)))).build();
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
