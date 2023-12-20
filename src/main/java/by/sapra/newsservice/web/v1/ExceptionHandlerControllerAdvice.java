package by.sapra.newsservice.web.v1;

import by.sapra.newsservice.web.v1.models.ErrorResponse;
import by.sapra.newsservice.web.v1.models.PaginationErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        PaginationErrorResponse errorResponse = new PaginationErrorResponse();

        List<String> errorList = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        errorResponse.setMessage(String.join(";", errorList));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        PaginationErrorResponse errorResponse = new PaginationErrorResponse();

        String message = ex.getLocalizedMessage();

        errorResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }
}
