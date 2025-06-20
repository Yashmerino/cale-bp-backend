package com.mlc.vdsr.exception;

import com.mlc.vdsr.dto.ErrorDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles exceptions thrown in endpoints.
 */
@RestControllerAdvice
@Validated
public class ApiExceptionHandler {
    /**
     * Handles the {@link ConstraintViolationException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        FieldsErrorsResponse fieldErrorResponse = new FieldsErrorsResponse();

        List<CustomFieldError> fieldErrors = new ArrayList<>();
        e.getConstraintViolations().forEach(error -> {
            CustomFieldError fieldError = new CustomFieldError();
            fieldError.setField(error.getPropertyPath().toString());
            fieldError.setMessage(error.getMessage());
            fieldErrors.add(fieldError);
        });

        fieldErrorResponse.setFieldErrors(fieldErrors);
        return new ResponseEntity<>(fieldErrorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the {@link MethodArgumentNotValidException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldsErrorsResponse fieldErrorResponse = new FieldsErrorsResponse();

        List<CustomFieldError> fieldErrors = new ArrayList<>();
        e.getAllErrors().forEach(error -> {
            CustomFieldError fieldError = new CustomFieldError();
            fieldError.setField(((FieldError) error).getField());
            fieldError.setMessage(error.getDefaultMessage());
            fieldErrors.add(fieldError);
        });

        fieldErrorResponse.setFieldErrors(fieldErrors);
        return new ResponseEntity<>(fieldErrorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the {@link UserNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link UsernameNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link EmailAlreadyTakenException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(value = {EmailAlreadyTakenException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleEmailAlreadyTakenException(EmailAlreadyTakenException e) {
        ErrorDTO errors = new ErrorDTO();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(e.getMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    /**
     * Handles the {@link UsernameAlreadyTakenException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(value = {UsernameAlreadyTakenException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException e) {
        ErrorDTO errors = new ErrorDTO();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(e.getMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    /**
     * Handles the {@link EventNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleEventNotFoundException(EventNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link RecruitmentNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(RecruitmentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleRecruitmentNotFoundException(RecruitmentNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link ProjectNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link PayrollNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(PayrollNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handlePayrollNotFoundException(PayrollNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link EmployeeRecordNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(EmployeeRecordNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleEmployeeRecordNotFoundException(EmployeeRecordNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link InvoiceNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(InvoiceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleInvoiceNotFoundException(InvoiceNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link BudgetNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(BudgetNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleBudgetNotFoundException(BudgetNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link ExpenseNotFoundException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(ExpenseNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleExpenseNotFoundException(ExpenseNotFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link AccessDeniedException}
     *
     * @param e is the thrown exception.
     * @return <code>ResponseEntity</code>
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
