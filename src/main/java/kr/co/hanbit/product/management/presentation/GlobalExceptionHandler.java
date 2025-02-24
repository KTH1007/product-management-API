package kr.co.hanbit.product.management.presentation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolatedException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errors = constraintViolations.stream()
                .map(constraintViolation ->
                        constraintViolation.getPropertyPath() + ", " +
                        constraintViolation.getMessage())
                .toList();
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> filedErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = filedErrors.stream()
                .map(filedError -> filedError.getField() + ", " + filedError.getDefaultMessage())
                .toList();
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
