package team.ojt7.recruitment.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.Interview.Status;

public class InterviewDto {
	private Long id;
	private String code;
	private InterviewNameDto interviewName;
	private Status status = Status.NOT_START_YET;
	private String comment;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate localDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime localTime;
	private ApplicantDto applicant;
	private UserDto owner;
	private LocalDateTime createdDateTime;
	private LocalDateTime updatedOn;

	public static InterviewDto of(Interview interview) {
		if (interview == null) {
			return null;
		} else {
			InterviewDto interviewtDto = new InterviewDto();
			interviewtDto.setId(interview.getId());
			interviewtDto.setCode(interview.getCode());
			interviewtDto.setInterviewName(InterviewNameDto.of(interview.getInterviewName()));
			interviewtDto.setStatus(interview.getStatus());
			interviewtDto.setComment(interview.getComment());
			interviewtDto.setApplicant(ApplicantDto.of(interview.getApplicant()));
			interviewtDto.setLocalDate(interview.getDateTime().toLocalDate());
			interviewtDto.setLocalTime(interview.getDateTime().toLocalTime());
			interviewtDto.setOwner(UserDto.of(interview.getOwner()));
			interviewtDto.setUpdatedOn(interview.getUpdatedOn());
			interviewtDto.setCreatedDateTime(interview.getCreatedDateTime());
			return interviewtDto;
		}
	}

	public static List<InterviewDto> ofList(List<Interview> interviews) {
		return interviews.stream().map(i -> of(i)).toList();
	}

	public static Interview parse(InterviewDto interviewDto) {
		if (interviewDto == null) {
			return null;
		} else {
			Interview interview = new Interview();
			interview.setId(interviewDto.getId());
			interview.setCode(interviewDto.getCode());
			interview.setInterviewName(InterviewNameDto.parse(interviewDto.getInterviewName()));
			interview.setStatus(interviewDto.getStatus());
			interview.setComment(interviewDto.getComment());
			interview.setApplicant(ApplicantDto.parse(interviewDto.getApplicant()));
			interview.setDateTime(LocalDateTime.of(interviewDto.getLocalDate(), interviewDto.getLocalTime()));
			interview.setOwner(UserDto.parse(interviewDto.getOwner()));
			interview.setUpdatedOn(interviewDto.getUpdatedOn());
			interview.setCreatedDateTime(interviewDto.getCreatedDateTime());
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

	public ApplicantDto getApplicant() {
		return applicant;
	}

	public void setApplicant(ApplicantDto applicant) {
		this.applicant = applicant;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public LocalTime getLocalTime() {
		return localTime;
	}

	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}

	public UserDto getOwner() {
		return owner;
	}

	public void setOwner(UserDto owner) {
		this.owner = owner;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getStatusColorClass() {
		return switch (status) {
		case NOT_START_YET -> "bg-primary text-white";
		case CANCELED -> "bg-info text-dark";
		case ON_HOLD -> "bg-orangered";
		case FAILED -> "bg-danger";
		case PASSED -> "bg-success";
		default -> null;
		};
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicant, code, comment, id, interviewName, localDate, localTime, status);
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
		return Objects.equals(applicant, other.applicant) && Objects.equals(code, other.code)
				&& Objects.equals(comment, other.comment) && Objects.equals(id, other.id)
				&& Objects.equals(interviewName, other.interviewName) && Objects.equals(localDate, other.localDate)
				&& Objects.equals(localTime, other.localTime) && status == other.status;
	}

	public boolean isStatusUpdatable() {
		return LocalDateTime.of(localDate, localTime).isBefore(LocalDateTime.now());
	}
}
