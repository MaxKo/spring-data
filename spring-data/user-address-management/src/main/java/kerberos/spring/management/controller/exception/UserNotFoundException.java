package kerberos.spring.management.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(HttpStatus status) {
        super(status);
    }

    public UserNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public UserNotFoundException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}