package team.ojt7.recruitment.model.dto;

import java.util.List;

import team.ojt7.recruitment.model.entity.Position;

public class PositionDto {
	private Long id;
	private String name;
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
	

}
