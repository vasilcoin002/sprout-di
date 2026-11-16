package org.sproutdi.core.exception;

public class BeanCreationException extends SproutException {
    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
