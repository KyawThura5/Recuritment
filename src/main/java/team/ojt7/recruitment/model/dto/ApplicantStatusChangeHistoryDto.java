package team.ojt7.recruitment.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.entity.ApplicantStatusChangeHistory;

public class ApplicantStatusChangeHistoryDto {

	private Long id;

	private Long applicantId;

	private ApplicantDto applicant;

	private Status status;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDateTime updatedOn;

	private String remark;

	private UserDto updatedBy;

	public static ApplicantStatusChangeHistoryDto of(ApplicantStatusChangeHistory entity) {
		if (entity == null) {
			return null;
		}

		ApplicantStatusChangeHistoryDto dto = new ApplicantStatusChangeHistoryDto();
		dto.setId(entity.getId());
		dto.setStatus(entity.getStatus());
		dto.setRemark(entity.getRemark());
		dto.setUpdatedOn(entity.getUpdatedOn());
		dto.setApplicant(ApplicantDto.of(entity.getApplicant()));
		dto.setUpdatedBy(UserDto.of(entity.getUpdatedBy()));
		return dto;
	}

	public static List<ApplicantStatusChangeHistoryDto> ofList(List<ApplicantStatusChangeHistory> entityList) {
		return entityList.stream().map(h -> of(h)).toList();
	}

	public static ApplicantStatusChangeHistory parse(ApplicantStatusChangeHistoryDto dto) {
		if (dto == null) {
			return null;
		}

		ApplicantStatusChangeHistory entity = new ApplicantStatusChangeHistory();
		entity.setId(dto.getId());
		entity.setStatus(dto.getStatus());
		entity.setRemark(dto.getRemark());
		entity.setUpdatedOn(dto.getUpdatedOn());
		entity.setUpdatedBy(UserDto.parse(dto.getUpdatedBy()));
		entity.setApplicant(ApplicantDto.parse(dto.getApplicant()));
		return entity;
	}

	public static List<ApplicantStatusChangeHistory> parseList(List<ApplicantStatusChangeHistoryDto> dtoList) {
		return dtoList.stream().map(h -> parse(h)).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApplicantDto getApplicant() {
		return applicant;
	}

	public void setApplicant(ApplicantDto applicant) {
		this.applicant = applicant;
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

	public Long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}

	public UserDto getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UserDto updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStatusColorClass() {
		return switch(status) {
		case NEW -> "bg-primary";
		case UNQULIFIED -> "bg-secondary";
		case QULIFIED -> "bg-info text-dark";
		case SELECTED -> "bg-orangered";
		case REJECTED -> "bg-danger";
		case JOB_OFFERED -> "bg-warning text-dark";
		case DID_NOT_JOINED -> "bg-dark";
		case HIRED -> "bg-success";
		default -> null;
		};
	}
}
