package com.douzone.mysite.exception;

import java.io.IOException;

public class PagingNumberFormatException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PagingNumberFormatException() {
		super("PagingNumberFormatException Occurs...");
	}
	
	public PagingNumberFormatException(NumberFormatException e) {
		super(e);
	}
}