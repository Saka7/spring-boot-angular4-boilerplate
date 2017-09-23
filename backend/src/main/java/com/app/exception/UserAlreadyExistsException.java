package com.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="User with this username or email already exists")
public class UserAlreadyExistsException extends RuntimeException {
}
