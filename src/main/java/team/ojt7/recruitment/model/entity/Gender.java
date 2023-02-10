package team.ojt7.recruitment.model.entity;

public enum Gender {
	MALE("Male"),
	FEMALE("Female"),
	OTHER("Others");
	private String displayName;
	Gender(String displayName){
		this.displayName=displayName;
	}
	public String getDisplayName() {
		return displayName;
		
	}
}
