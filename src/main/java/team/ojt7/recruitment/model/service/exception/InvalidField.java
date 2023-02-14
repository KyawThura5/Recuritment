package team.ojt7.recruitment.model.service.exception;

import java.util.Objects;

public class InvalidField {
	private String field;
	private String code;
	private String message;
	
	public InvalidField(String filed, String code, String message) {
		this.field = filed;
		this.code = code;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, field, message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvalidField other = (InvalidField) obj;
		return Objects.equals(code, other.code) && Objects.equals(field, other.field)
				&& Objects.equals(message, other.message);
	}
	

}
