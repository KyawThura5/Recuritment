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
	private InterviewName interViewName;

	@Enumerated(EnumType.STRING)
	private Status status;
	private String comment;

	@Column(name = "date_time")
	private LocalDateTime dateTime;

	@ManyToOne
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;

	public enum Status {
		ON_HOLD("On-hold"), CANCELED("Canceled"), PASSED("Passed"), FAILED("Failed");

		private String displayName;

		Status(String displayName) {
			this.displayName = displayName;
		}

		public String getDisplayName() {
			return displayName;
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
		return interViewName;
	}

	public void setInterviewName(InterviewName interViewName) {
		this.interViewName = interViewName;
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

	@Override
	public int hashCode() {
		return Objects.hash(applicant, code, comment, dateTime, id, interViewName, status);
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
				&& Objects.equals(id, other.id) && Objects.equals(interViewName, other.interViewName)
				&& status == other.status;
	}

}
