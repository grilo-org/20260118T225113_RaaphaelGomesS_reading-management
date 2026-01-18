package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoteCategoryException extends Exception {

    private HttpStatus status;

    public NoteCategoryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
