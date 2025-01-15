package org.ms.timepro.manager.exception;

public class ApplicationValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String erroHandle;

    public static ApplicationValidationException createWith(String erroHandle) {
        return new ApplicationValidationException(erroHandle);
    }

    public ApplicationValidationException(String erroHandle) {
        this.erroHandle = erroHandle;
    }

    @Override
    public String getMessage() {
        return erroHandle;
    }
}