package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
	
	@Column(name = "created_date", nullable = false, columnDefinition = "DATE DEFAULT (CURRENT_DATE)", updatable = false)
	private LocalDate createdDate;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "VARCHAR(100) DEFAULT 'OPENING'")
	private Status status;

	private String comment;
	
	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean deleted;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "vacancy_id")
	private List<RequirePosition> requirePositions;

	public enum Status {
		OPENING("Opening"), CLOSED("Closed");

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

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public List<RequirePosition> getRequirePositions() {
		return requirePositions;
	}

	public void setRequirePositions(List<RequirePosition> requirePositions) {
		this.requirePositions = requirePositions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, comment, createdDate, deleted, dueDate, id, requirePositions, status);
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
				&& Objects.equals(createdDate, other.createdDate) && deleted == other.deleted
				&& Objects.equals(dueDate, other.dueDate) && Objects.equals(id, other.id)
				&& Objects.equals(requirePositions, other.requirePositions) && status == other.status;
	}

}
