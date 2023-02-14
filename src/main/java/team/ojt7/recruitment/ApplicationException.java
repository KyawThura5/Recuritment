package team.ojt7.recruitment;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationException() {}
	
	public ApplicationException(String message) {
		super(message);
	}
}
