package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import team.ojt7.recruitment.model.entity.RequirePosition;

public class RequirePositionDto {

	private Long id;
	private boolean foc;
	private int count;
	private PositionDto position;
	
	public static RequirePositionDto of(RequirePosition entity) {
		return null;
	}
	
	public static List<RequirePositionDto> ofList(List<RequirePosition> entityList) {
		return entityList.stream().map(e -> of(e)).toList();
	}
	
	public static RequirePosition parse(RequirePositionDto dto) {
		return null;
	}
	
	public static List<RequirePosition> parseList(List<RequirePositionDto> dtoList) {
		return dtoList.stream().map(d -> parse(d)).toList();
	}

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

	public PositionDto getPosition() {
		return position;
	}

	public void setPosition(PositionDto position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		return Objects.hash(count, foc, id, position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequirePositionDto other = (RequirePositionDto) obj;
		return count == other.count && foc == other.foc && Objects.equals(id, other.id)
				&& Objects.equals(position, other.position);
	}

}
