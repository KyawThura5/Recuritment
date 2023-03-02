package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import team.ojt7.recruitment.model.entity.Applicant.Status;

@Entity
@Table(name = "applicant_status_change_history")
public class ApplicantStatusChangeHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Status status;

	@Column(name = "updated_on", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedOn;

	private String remark;

	@ManyToOne
	@JoinColumn(name = "updated_user_id")
	private User updatedBy;

	@ManyToOne
	private Applicant applicant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicant, id, remark, status, updatedBy, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicantStatusChangeHistory other = (ApplicantStatusChangeHistory) obj;
		return Objects.equals(applicant, other.applicant) && Objects.equals(id, other.id)
				&& Objects.equals(remark, other.remark) && status == other.status
				&& Objects.equals(updatedBy, other.updatedBy) && Objects.equals(updatedOn, other.updatedOn);
	}

}
