package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vacancy")
public class Vacancy implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String code;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@Column(name = "created_date_time", nullable = false, updatable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdDateTime;

	@Column(name = "due_date", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "VARCHAR(100) DEFAULT 'OPENING'")
	private Status status;

	@Column(name = "comment", columnDefinition = "VARCHAR(1000)")
	private String comment;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "created_user_id")
	private User createdUser;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "vacancy_id")
	private List<RequirePosition> requirePositions = new ArrayList<>();

	public enum Status {
		OPENING("Opening"), CLOSED("Closed"), EXPIRED("Expired");

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		this.isDeleted = deleted;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
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

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public List<RequirePosition> getRequirePositions() {
		return requirePositions;
	}

	public void setRequirePositions(List<RequirePosition> requirePositions) {
		this.requirePositions = requirePositions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, comment, createdDateTime, createdUser, department, dueDate, id, isDeleted,
				requirePositions, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacancy other = (Vacancy) obj;
		return Objects.equals(code, other.code) && Objects.equals(comment, other.comment)
				&& Objects.equals(createdDateTime, other.createdDateTime) && Objects.equals(createdUser, other.createdUser)
				&& Objects.equals(department, other.department) && Objects.equals(dueDate, other.dueDate)
				&& Objects.equals(id, other.id) && isDeleted == other.isDeleted
				&& Objects.equals(requirePositions, other.requirePositions) && status == other.status;
	}

	

}
