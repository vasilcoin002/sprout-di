package org.sproutdi.core.exception;

public class SproutException extends RuntimeException {
    public SproutException(String message) {
        super(message);
    }

    public SproutException(String message, Throwable cause) {
        super(message, cause);
    }
}
