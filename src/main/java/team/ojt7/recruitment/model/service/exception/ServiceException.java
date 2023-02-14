package team.ojt7.recruitment.model.service.exception;

import team.ojt7.recruitment.ApplicationException;

public class ServiceException extends ApplicationException {

	private static final long serialVersionUID = 1L;
	
	public ServiceException() {}
	
	public ServiceException(String message) {
		super(message);
	}

}
