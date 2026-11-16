package org.sproutdi.core.exception;

public class CircularDependencyException extends SproutException {
    public CircularDependencyException(String message) {
        super(message);
    }
}
