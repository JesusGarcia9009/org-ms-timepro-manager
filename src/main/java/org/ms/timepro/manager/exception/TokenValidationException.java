package org.ms.timepro.manager.exception;

public class TokenValidationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int statusCode;

	public TokenValidationException(String message, int statusCode, Throwable cause) {
		super(message, cause);
        this.statusCode = statusCode;
    }
	
    public int getStatusCode() {
        return statusCode;
    }
}