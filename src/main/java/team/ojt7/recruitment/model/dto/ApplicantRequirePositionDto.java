package team.ojt7.recruitment.model.dto;

public class ApplicantRequirePositionDto {

	private Long id;
	private boolean foc;

	private TeamDto team;

	private PositionDto position;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isFoc() {
		return foc;
	}

	public void setFoc(boolean foc) {
		this.foc = foc;
	}

	public TeamDto getTeam() {
		return team;
	}

	public void setTeam(TeamDto team) {
		this.team = team;
	}

	public PositionDto getPosition() {
		return position;
	}

	public void setPosition(PositionDto position) {
		this.position = position;
	}
	
	
}
