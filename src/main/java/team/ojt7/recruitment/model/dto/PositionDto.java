package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import team.ojt7.recruitment.model.entity.Position;

public class PositionDto {
	private Long id;
	
	@NotBlank(message = "{notBlank.position.name}")
	private String name;
	private boolean isDeleted;
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
	public static List<PositionDto> ofList(List<Position> position) {
		
		return position.stream().map(p -> PositionDto.of(p)).toList();
	}
	public static PositionDto of(Position p) {
		if(p==null) {
			return null;
		}
		PositionDto dto=new PositionDto();
		dto.setId(p.getId());
		dto.setName(p.getName());
		return dto;
	}
	
	public static Position parse(PositionDto positionDto) {
		if(positionDto==null) {
			return null;
		}
		Position position = new Position();
		position.setId(positionDto.getId());
		position.setName(positionDto.getName());
		
		return position;
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
		PositionDto other = (PositionDto) obj;
		return Objects.equals(id, other.id) && isDeleted == other.isDeleted && Objects.equals(name, other.name);
	}
	
	
}
