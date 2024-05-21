package co.com.icesi.Eshop.error.util;

import co.com.icesi.Eshop.error.exception.ErrorDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class ErrorDetailsManager {
    public static String fields = "";
    public static ErrorDetail createDetail(String message, EshopErrorCode errorCode){
        return ErrorDetail.builder().errorCode(errorCode).errorMessage(message).build();
    }

    public static ErrorDetail[] sendDetails(ErrorDetail ... details){
        return details;
    }

    public static String getMissingFiled(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        if (!bindingResult.hasErrors()) {
            return "";
        }
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        fieldErrorList.stream().map(FieldError::getField).forEach(fieldName -> fields+=fieldName+" ");
        System.out.println(fields);


        return fields;
    }
}
