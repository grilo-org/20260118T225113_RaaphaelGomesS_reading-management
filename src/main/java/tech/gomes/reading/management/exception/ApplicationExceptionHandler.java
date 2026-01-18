package tech.gomes.reading.management.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        log.warn("Falha na validação: {}", ex.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("errors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity handlerUserException(UserException e) {

        UserException exception = new UserException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity handlerLibraryException(LibraryException e) {

        LibraryException exception = new LibraryException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity handlerBookException(BookException e) {

        BookException exception = new BookException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(BookTemplateException.class)
    public ResponseEntity handlerBookTemplateException(BookTemplateException e) {

        BookTemplateException exception = new BookTemplateException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(NoteCategoryException.class)
    public ResponseEntity handlerNoteCategoryException(NoteCategoryException e) {

        NoteCategoryException exception = new NoteCategoryException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(NoteException.class)
    public ResponseEntity handlerNoteException(NoteException e) {

        NoteException exception = new NoteException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(SuggestionException.class)
    public ResponseEntity handlerSuggestionException(SuggestionException e) {

        SuggestionException exception = new SuggestionException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}
