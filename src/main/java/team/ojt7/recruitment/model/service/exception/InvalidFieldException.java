package team.ojt7.recruitment.model.service.exception;

public class InvalidFieldException extends ServiceException {
	
	private static final long serialVersionUID = 1L;
	private String field;
	private String code;
	
	public InvalidFieldException(String filed, String code, String message) {
		super(message);
		this.field = filed;
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	

}
