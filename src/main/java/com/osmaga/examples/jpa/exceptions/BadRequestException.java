package com.osmaga.examples.jpa.exceptions;

import com.osmaga.examples.jpa.constants.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private ErrorCode errorCode;

	private static final long serialVersionUID = 1L;

	public BadRequestException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
	}
}
