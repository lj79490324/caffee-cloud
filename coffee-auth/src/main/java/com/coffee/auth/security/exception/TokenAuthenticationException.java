package com.coffee.auth.security.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {
    public TokenAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public TokenAuthenticationException(String msg) {
        super(msg);
    }
}
