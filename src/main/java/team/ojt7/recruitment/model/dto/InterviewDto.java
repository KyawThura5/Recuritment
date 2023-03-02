package team.ojt7.recruitment.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.Interview.Status;

public class InterviewDto {
	private Long id;
	private String code;
	private InterviewNameDto interviewName;
	private Status status;
	private String comment;
	private LocalDateTime dateTime;
	private ApplicantDto applicant;
	

	public static InterviewDto of(Interview interview) {
		if(interview==null)
		{
			return null;
		}
		else {
		InterviewDto interviewtDto = new InterviewDto();
		interviewtDto.setId(interview.getId());
		interviewtDto.setCode(interview.getCode());
		interviewtDto.setInterviewName(InterviewNameDto.of(interview.getInterviewName()));
		interviewtDto.setStatus(interview.getStatus());
		interviewtDto.setComment(interview.getComment());
		interviewtDto.setApplicant(ApplicantDto.of(interview.getApplicant()));
		return interviewtDto;
		}
	}
	
	public static List<InterviewDto> ofList(List<Interview> interviews) {
		return interviews.stream().map(i -> of(i)).toList();
	}
	
	public static Interview parse(InterviewDto interviewDto) {
		if(interviewDto==null)
		{
			return null;
		}
		else {
		Interview interview = new Interview();
		interview.setId(interviewDto.getId());
		interview.setCode(interviewDto.getCode());
		interview.setInterviewName(InterviewNameDto.parse(interviewDto.getInterviewName()));
		interview.setStatus(interviewDto.getStatus());
		interview.setComment(interviewDto.getComment());
		interview.setApplicant(ApplicantDto.parse(interviewDto.getApplicant()));
		return interview;
		}
	}
	
	public static List<Interview> parseList(List<InterviewDto> interviewDto) {
		return interviewDto.stream().map(i -> parse(i)).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public InterviewNameDto getInterviewName() {
		return interviewName;
	}

	public void setInterviewName(InterviewNameDto interviewName) {
		this.interviewName = interviewName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	

	public ApplicantDto getApplicant() {
		return applicant;
	}

	public void setApplicant(ApplicantDto applicant) {
		this.applicant = applicant;
	}
	
	
}
