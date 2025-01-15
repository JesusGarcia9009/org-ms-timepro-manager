package org.ms.timepro.manager.exception;

public class UserNotAuthException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String erroHandle;

    public static UserNotAuthException createWith(String erroHandle) {
        return new UserNotAuthException(erroHandle);
    }

    public UserNotAuthException(String erroHandle) {
        this.erroHandle = erroHandle;
    }

    @Override
    public String getMessage() {
        return erroHandle;
    }
}
