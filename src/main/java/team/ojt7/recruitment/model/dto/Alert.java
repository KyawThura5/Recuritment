package team.ojt7.recruitment.model.dto;

public class Alert {

	private String message;
	
	private String cssClass;
	
	public Alert() {}

	public Alert(String message, String cssClass) {
		super();
		this.message = message;
		this.cssClass = cssClass;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	
}
