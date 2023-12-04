package pl.lodz.p.fileSummarizer.exception;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiExceptionResponse apiExceptionResponse =
                new ApiExceptionResponse("INVALID ARGUMENT PASSED", HttpStatus.BAD_REQUEST,
                        errors, LocalDateTime.now());

        return handleExceptionInternal(ex, apiExceptionResponse, headers, apiExceptionResponse.getHttpStatus(),
                request);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                       WebRequest request) {
        ApiExceptionResponse apiExceptionResponse =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiExceptionResponse, new HttpHeaders(), apiExceptionResponse.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        return new ResponseEntity<>(
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.METHOD_NOT_ALLOWED,
                        List.of(request.getDescription(false)), LocalDateTime.now()),
                new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }



    @ExceptionHandler(NumberFormatException.class)
    public final ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex,
                                                                    WebRequest request) {
        ApiExceptionResponse apiExceptionResponse =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiExceptionResponse, new HttpHeaders(), apiExceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(EmptyFileContentException.class)
    public final ResponseEntity<Object> handleCannotReadTextFromPdfException(EmptyFileContentException ex,
                                                                             WebRequest request) {
        ApiExceptionResponse apiException =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public final ResponseEntity<Object> handleFileSizeLimitExceededException(FileSizeLimitExceededException ex,
                                                                             WebRequest request) {
        ApiExceptionResponse apiExceptionResponse =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiExceptionResponse, new HttpHeaders(), apiExceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(FileTooBigException.class)
    public final ResponseEntity<Object> handleFileTooBigException(FileTooBigException ex,
                                                                  WebRequest request) {
        ApiExceptionResponse apiException =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }


    @ExceptionHandler(FileContentTooLongException.class)
    public final ResponseEntity<Object> handleFileContentTooLongException(FileContentTooLongException ex,
                                                                          WebRequest request) {
        ApiExceptionResponse apiException =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }

    @ExceptionHandler(IllegalFileExtensionException.class)
    public final ResponseEntity<Object> handleIllegalFileExtensionException(IllegalFileExtensionException ex,
                                                                            WebRequest request) {
        ApiExceptionResponse apiException =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }


    @ExceptionHandler(InvalidContextLengthException.class)
    public final ResponseEntity<Object> handleInvalidContextLengthException(InvalidContextLengthException ex,
                                                                            WebRequest request) {
        ApiExceptionResponse apiException =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }


    @ExceptionHandler(UnsupportedLanguageException.class)
    public final ResponseEntity<Object> handleUnsupportedLanguageException(UnsupportedLanguageException ex,
                                                                           WebRequest request) {
        ApiExceptionResponse apiException =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }

    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<Object> handleGeneralException(GeneralException ex,
                                                                             WebRequest request) {
        ApiExceptionResponse apiExceptionResponse =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiExceptionResponse, new HttpHeaders(), apiExceptionResponse.getHttpStatus());
    }


    @ExceptionHandler(InvalidPasswordException.class)
    public final ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex,
                                                                    WebRequest request) {
        ApiExceptionResponse apiExceptionResponse =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiExceptionResponse, new HttpHeaders(), apiExceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex,
                                                                       WebRequest request) {
        ApiExceptionResponse apiExceptionResponse =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiExceptionResponse, new HttpHeaders(), apiExceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(OpenAiApiException.class)
    public final ResponseEntity<Object> handleOpenAiApiException(OpenAiApiException ex,
                                                               WebRequest request) {
        ApiExceptionResponse apiExceptionResponse =
                new ApiExceptionResponse(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiExceptionResponse, new HttpHeaders(), apiExceptionResponse.getHttpStatus());
    }

}




