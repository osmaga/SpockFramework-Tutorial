package com.osmaga.examples.jpa.exceptions;

import com.osmaga.examples.jpa.constants.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TeacherAddressNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TeacherAddressNotFoundException() {
		super("Teacher does not exist");
	}

	private ErrorCode errorCode;

	public TeacherAddressNotFoundException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
	}

}
