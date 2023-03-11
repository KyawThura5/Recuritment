package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "interview")
public class Interview implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "interview_name_id")
	private InterviewName interviewName;

	@Enumerated(EnumType.STRING)
	private Status status;
	private String comment;

	@Column(name = "date_time")
	private LocalDateTime dateTime;

	@ManyToOne
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;
	
	@ManyToOne
	private User owner;
	
	@Column(name = "created_date_time", nullable = false, updatable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdDateTime;
	
	private LocalDateTime updatedOn;

	public enum Status {
		NOT_START_YET("Not Started Yet", 1),ON_HOLD("On-hold", 2), CANCELLED("Cancelled", 3), FAILED("Failed", 4), PASSED("Passed", 5);

		private String displayName;
		private int step;

		Status(String displayName, int step) {
			this.displayName = displayName;
			this.step = step;
		}

		public String getDisplayName() {
			return displayName;
		}
		
		public int getStep() {
			return step;
		}

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

	public InterviewName getInterviewName() {
		return interviewName;
	}

	public void setInterviewName(InterviewName interViewName) {
		this.interviewName = interViewName;
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

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
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

	@Override
	public int hashCode() {
		return Objects.hash(applicant, code, comment, dateTime, id, interviewName, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interview other = (Interview) obj;
		return Objects.equals(applicant, other.applicant) && Objects.equals(code, other.code)
				&& Objects.equals(comment, other.comment) && Objects.equals(dateTime, other.dateTime)
				&& Objects.equals(id, other.id) && Objects.equals(interviewName, other.interviewName)
				&& status == other.status;
	}

}
