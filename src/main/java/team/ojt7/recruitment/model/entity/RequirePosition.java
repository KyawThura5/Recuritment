package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "require_position")
public class RequirePosition implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "is_foc", nullable = false, columnDefinition = "boolean default false")
	private boolean foc;
	
	@Column(nullable = false)
	private int count;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public boolean isEqualsPosition(RequirePosition rp) {
		return Objects.equals(this.position, rp.position) && Objects.equals(this.foc, rp.foc) && Objects.equals(this.team, rp.team);
	}

	@Override
	public int hashCode() {
		return Objects.hash(count, foc, id, position, team);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequirePosition other = (RequirePosition) obj;
		return count == other.count && foc == other.foc && Objects.equals(id, other.id)
				&& Objects.equals(position, other.position) && Objects.equals(team, other.team);
	}

	
	
}
