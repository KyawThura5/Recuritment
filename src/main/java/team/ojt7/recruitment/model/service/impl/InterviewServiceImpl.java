package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantStatusChangeHistoryDto;
import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewSearch;
import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.Interview.Status;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.repo.InterviewRepo;
import team.ojt7.recruitment.model.service.ApplicantStatusChangeHistoryService;
import team.ojt7.recruitment.model.service.InterviewService;
import team.ojt7.recruitment.util.generator.InterviewCodeGenerator;

@Service
public class InterviewServiceImpl implements InterviewService {
	
	@Autowired
	private InterviewRepo interviewRepo;
	
	@Autowired
	private InterviewCodeGenerator interviewCodeGenerator;
	
	@Autowired
	private ApplicantStatusChangeHistoryService applicantStatusChangeHistoryService;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public Page<InterviewDto> search(InterviewSearch search) {
		
		String keyword = search.getKeyword() == null ? "%%" : "%" + search.getKeyword() + "%";
		
		Pageable pageable = PageRequest.of(search.getPage() - 1, search.getSize(),search.getSort().getSort());
		LocalDateTime dateFrom=search.getDateFrom()==null ? null : search.getDateFrom().atStartOfDay();
		LocalDateTime dateTo=search.getDateTo()==null ? null : search.getDateTo().plusDays(1).atStartOfDay();
		Page<Interview> interviews =interviewRepo.search(keyword,dateFrom,dateTo,search.getStatus(), pageable); 
		Pageable interviewsPageable = interviews.getPageable();
		Page<InterviewDto> page = new PageImpl<InterviewDto>(InterviewDto.ofList(interviews.getContent()), interviewsPageable, interviews.getTotalElements());
		return page;	
	}

	@Override
	@Transactional
	public InterviewDto save(Interview interview) {
		if (interview.getId() != null) {
			Interview original = interviewRepo.findById(interview.getId()).get();
			interview.setCreatedDateTime(original.getCreatedDateTime());
			interview.setComment(original.getComment());
			interview.setOwner(original.getOwner());
			interview.setUpdatedOn(original.getUpdatedOn());
			interview.setStatus(original.getStatus());
		} else if (interview.getOwner() == null) {
			User loginUser = (User) session.getAttribute("loginUser");
			interview.setOwner(loginUser);
		}
		interview.setUpdatedOn(LocalDateTime.now());
		InterviewDto interviewDto = InterviewDto.of(interviewRepo.save(interview));
		return interviewDto;
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		interviewRepo.deleteById(id);
		return true;
	}

	@Override
	public InterviewDto generateNewWithCode() {
		Long maxId = interviewRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		InterviewDto interviewDto = new InterviewDto();
		interviewDto.setCode(interviewCodeGenerator.generate(id));
		return interviewDto;
	}

	@Override
	public Optional<InterviewDto> findById(Long id) {
		if (id == null) {
			return Optional.ofNullable(null);
		}
		Interview interview=interviewRepo.findById(id).orElse(null);
		return Optional.ofNullable(InterviewDto.of(interview));
	}

	@Override
	public void saveInterviewStatus(Long id, Status status, String comment, boolean applicantStatusChecked, team.ojt7.recruitment.model.entity.Applicant.Status applicantStatus, String applicantComment) {
		if (id == null) {
			return;
		}
		Interview interview=interviewRepo.findById(id).orElse(null);
		interview.setStatus(status);
		interview.setComment(comment);
		save(interview);
		if (applicantStatusChecked) {
			Applicant applicant = interview.getApplicant();
			ApplicantStatusChangeHistoryDto statusHistory = new ApplicantStatusChangeHistoryDto();
			statusHistory.setApplicantId(applicant.getId());
			statusHistory.setStatus(applicantStatus);
			statusHistory.setRemark(applicantComment);
			applicantStatusChangeHistoryService.save(statusHistory);
		}
	}

	@Override
	public InterviewDto getCurrentStatus(Long id) {
		
		Interview interview=interviewRepo.findById(id).orElse(null);
		return InterviewDto.of(interview);
	}

	@Override
	public List<InterviewDto> findAll() {
		return InterviewDto.ofList(interviewRepo.findAll());
	}

	@Override
	public List<InterviewDto> findCompletedInterivews(LocalDate dateFrom, LocalDate dateTo) {
		LocalDateTime dateTimeFrom = dateFrom == null ? null : dateFrom.atStartOfDay();
		LocalDateTime dateTimeTo = dateTo == null ? null : dateTo.plusDays(1).atStartOfDay();
		return InterviewDto.ofList(interviewRepo.findCompletedInterviews(dateTimeFrom, dateTimeTo));
	}

	
}
