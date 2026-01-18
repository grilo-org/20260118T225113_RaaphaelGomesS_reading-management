package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LibraryException extends Exception {

    private HttpStatus status;

    public LibraryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
