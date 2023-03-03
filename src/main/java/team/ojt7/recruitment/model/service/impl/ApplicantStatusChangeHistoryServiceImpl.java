package team.ojt7.recruitment.model.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.ApplicantStatusChangeHistoryDto;
import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.ApplicantStatusChangeHistory;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.repo.ApplicantStatusChangeHistoryRepo;
import team.ojt7.recruitment.model.service.ApplicantStatusChangeHistoryService;

@Service
public class ApplicantStatusChangeHistoryServiceImpl implements ApplicantStatusChangeHistoryService {

	@Autowired
	private ApplicantStatusChangeHistoryRepo applicantStatusChangeHistoryRepo;
	
	@Autowired
	private ApplicantRepo applicantRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public ApplicantStatusChangeHistoryDto save(ApplicantStatusChangeHistoryDto dto) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		Applicant applicant = applicantRepo.findById(dto.getApplicantId()).get();
		applicant.setStatus(dto.getStatus());
		ApplicantStatusChangeHistory asch = ApplicantStatusChangeHistoryDto.parse(dto);
		asch.setApplicant(applicant);
		asch.setUpdatedBy(loginUser);
		return ApplicantStatusChangeHistoryDto.of(applicantStatusChangeHistoryRepo.save(asch));
	}

	@Override
	public List<ApplicantStatusChangeHistoryDto> findAllByApplicantId(Long applicantId) {
		List<ApplicantStatusChangeHistory> entityList = applicantStatusChangeHistoryRepo.findByAppliacantId(applicantId);
		return ApplicantStatusChangeHistoryDto.ofList(entityList);
	}
	
	@Override
	public ApplicantStatusChangeHistoryDto getCurrentStatus(Long applicantId) {
		Applicant applicant = applicantRepo.findById(applicantId).get();
		ApplicantStatusChangeHistoryDto dto = new ApplicantStatusChangeHistoryDto();
		dto.setStatus(applicant.getStatus());
		dto.setApplicant(ApplicantDto.of(applicant));
		dto.setApplicantId(applicantId);
		return dto;
	}

}
