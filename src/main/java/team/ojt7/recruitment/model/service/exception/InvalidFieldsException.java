package team.ojt7.recruitment.model.service.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidFieldsException extends ServiceException {

	private static final long serialVersionUID = 1L;

	private List<InvalidField> fields;
	
	public InvalidFieldsException() {
		fields = new ArrayList<>();
	}

	public List<InvalidField> getFields() {
		return fields;
	}

	public void setFields(List<InvalidField> exceptions) {
		this.fields = exceptions;
	}
	
	public void addField(InvalidField e) {
		this.fields.add(e);
	}
	
	public void removeField(InvalidField e) {
		this.fields.remove(e);
	}
	
	public boolean hasFields() {
		return !fields.isEmpty();
	}
}
