package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import team.ojt7.recruitment.model.entity.InterviewName;

public class InterviewNameDto {

	private Long id;
	@NotBlank(message="{NotBlank.interview.name")
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
	
	public static InterviewNameDto of(InterviewName interview) {
		if(interview==null)
		{
			return null;
		}
		else {
		InterviewNameDto interviewDto = new InterviewNameDto();
		interviewDto.setId(interview.getId());
		interviewDto.setName(interview.getName());
		interviewDto.setDeleted(interview.isDeleted());
		
		return interviewDto;
		}
	}
	
	public static List<InterviewNameDto> ofList(List<InterviewName> interviews) {
		return interviews.stream().map(d -> of(d)).toList();
	}
	
	public static InterviewName parse(InterviewNameDto interviewDto) {
		if(interviewDto==null)
		{
			return null;
		}
		else {
		InterviewName interview = new InterviewName();
		interview.setId(interviewDto.getId());
		interview.setName(interviewDto.getName());
		interview.setDeleted(interviewDto.isDeleted());
		
		return interview;
		}
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
		InterviewNameDto other = (InterviewNameDto) obj;
		return Objects.equals(id, other.id) && isDeleted == other.isDeleted && Objects.equals(name, other.name);
	}
	
	
	
}
