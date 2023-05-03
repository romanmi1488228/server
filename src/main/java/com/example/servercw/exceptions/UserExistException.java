package com.example.servercw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * exception which throws 404 http status if user exist in account table
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserExistException extends RuntimeException {
}
