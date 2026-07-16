package com.example.MoneyManagement.Exception;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiErrorResponse buildErrorResponse(
            HttpStatus status,String message,String path){
        return ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception, HttpServletRequest request){
        log.error("Resource not found",exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateResourceException(
            DuplicateResourceException exception, HttpServletRequest request){

        log.error("Duplicate resources : {}",exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            BadRequestException exception, HttpServletRequest request){
        log.error("Bad request : {}",exception.getMessage());
        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);

    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException exception, HttpServletRequest request){
        log.warn("Invalid login attempt : {} ",exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)

    public ResponseEntity<ApiErrorResponse> handleUnauthorizedException(
            UnauthorizedException exception, HttpServletRequest request) {
        log.warn("Unauthorized access : {}", exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);

    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleForbiddenException(
            ForbiddenException exception, HttpServletRequest request) {
        log.warn("Access denied : {}",exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.FORBIDDEN,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiErrorResponse> handleFileStorageException(
            FileStorageException exception, HttpServletRequest request) {
        log.error("File storage error : {}",exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(ExcelExportException.class)
    public ResponseEntity<ApiErrorResponse> handleExcelExportException(
            ExcelExportException exception, HttpServletRequest request) {
        log.error("Excel export failed : {}",exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailSendingException(
            EmailSendingException exception, HttpServletRequest request) {
        log.error("Email sending failed :{}" ,exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->error.getField() + " : "+error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Validation failed : {}", message);

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);


    }
    @ExceptionHandler(ConstraintViolationException.class)

    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
            ConstraintViolationException exception, HttpServletRequest request) {

        String message = exception.getConstraintViolations()
                .stream()
                .map(violation ->
                        violation.getPropertyPath() + " : "+ violation.getMessage())
                .collect(Collectors.joining(", "));

        log.warn("contrsint violation : {}",message);

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)

    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        String message=String.format( "Invalid value '%s' for parameter '%s'. Expected type: %s.",
                exception.getValue(),
                exception.getName(),
                exception.getRequiredType()!=null
                ? exception.getRequiredType().getSimpleName()
                        :"Unknow");

        log.warn("Method argument type mismatch:{}");

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)

    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception, HttpServletRequest request) {

        String message = "Request body is missing, malformed, or contains invalid data.";
        log.warn("Invalid request body:{}",exception.getMessage());

        ApiErrorResponse response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)

    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(
            AccessDeniedException exception, HttpServletRequest request){
        log.warn("Access denied for request [{} {}] : {}",
                request.getMethod(),
                request.getRequestURI(),
                exception.getMessage());

        String message = "You do not have permission to access this resource.";

        ApiErrorResponse response= buildErrorResponse(
                HttpStatus.FORBIDDEN,
                message,
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

}
