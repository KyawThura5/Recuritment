package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import team.ojt7.recruitment.model.entity.Interview;

public class InterviewDto {

	private Long id;
	private String name;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	private boolean isDeleted;
	
	public static InterviewDto of(Interview interview) {
		InterviewDto interviewDto = new InterviewDto();
		interviewDto.setId(interview.getId());
		interviewDto.setName(interview.getName());
		interviewDto.setDeleted(interview.isDeleted());
		
		return interviewDto;
	}
	
	public static List<InterviewDto> ofList(List<Interview> interviews) {
		return interviews.stream().map(d -> of(d)).toList();
	}
	
	public static Interview parse(InterviewDto interviewDto) {
		Interview interview = new Interview();
		interview.setId(interviewDto.getId());
		interview.setName(interviewDto.getName());
		interview.setDeleted(interviewDto.isDeleted());
		
		return interview;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isDeleted, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterviewDto other = (InterviewDto) obj;
		return Objects.equals(id, other.id) && isDeleted == other.isDeleted && Objects.equals(name, other.name);
	}
	
	
	
}
