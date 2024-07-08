package org.platkmframework.database.crud.domain.base.exception;

public class CrudException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CrudException() {
		super(); 
	}

	public CrudException(String message) {
		super(message); 
	}

}
