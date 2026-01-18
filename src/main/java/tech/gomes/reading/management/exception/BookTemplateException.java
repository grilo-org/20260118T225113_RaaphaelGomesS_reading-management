package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookTemplateException extends Exception {

    private HttpStatus status;

    public BookTemplateException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
