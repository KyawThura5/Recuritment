package team.ojt7.recruitment.event;

import team.ojt7.recruitment.model.entity.User;

public class UserUpdateEvent {
	
	private User user;

	public UserUpdateEvent(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
