package com.spring.reactive;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;


@RestControllerAdvice
public class GlobalErroHandler {

	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(GlobalErroHandler.class);
	
	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<String> handleRequestBodyError(WebExchangeBindException ex)
	{
		logger.error("erroe caught in handleRequestBody: {}",ex.getMessage(),ex);
		var error=ex.getBindingResult().getAllErrors().stream()
		.map(DefaultMessageSourceResolvable::getDefaultMessage)
		.sorted()
		.collect(Collectors.joining(" ,"));
		logger.error("Error is : {}",error);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
