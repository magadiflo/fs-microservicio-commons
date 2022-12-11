package com.magadiflo.commons.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> registroDuplicado(SQLIntegrityConstraintViolationException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("full_message", e.getMessage());
		response.put("message", "Registro duplicado");
		return ResponseEntity.badRequest().body(response);
	}

}
