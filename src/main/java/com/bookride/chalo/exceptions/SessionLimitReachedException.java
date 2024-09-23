package com.bookride.chalo.exceptions;

public class SessionLimitReachedException extends RuntimeException{

    public SessionLimitReachedException(String message) {
        super(message);
    }

    public SessionLimitReachedException() {
    }
}
