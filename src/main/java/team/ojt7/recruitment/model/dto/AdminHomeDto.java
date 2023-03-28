package team.ojt7.recruitment.model.dto;

public class AdminHomeDto {

	private long activeUserCount;
	private long departmentCount;
	private long teamCount;
	private long positionCount;

	public long getActiveUserCount() {
		return activeUserCount;
	}

	public void setActiveUserCount(long activeUserCount) {
		this.activeUserCount = activeUserCount;
	}

	public long getDepartmentCount() {
		return departmentCount;
	}

	public void setDepartmentCount(long departmentCount) {
		this.departmentCount = departmentCount;
	}

	public long getTeamCount() {
		return teamCount;
	}

	public void setTeamCount(long teamCount) {
		this.teamCount = teamCount;
	}

	public long getPositionCount() {
		return positionCount;
	}

	public void setPositionCount(long positionCount) {
		this.positionCount = positionCount;
	}

}
