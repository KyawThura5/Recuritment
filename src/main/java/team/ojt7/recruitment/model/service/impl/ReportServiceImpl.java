package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.CandidateCountInfo;
import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.dto.InterviewStageInfoByPosition;
import team.ojt7.recruitment.model.dto.InterviewStageInfoDto;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceByPositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceReportDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.service.ApplicantService;
import team.ojt7.recruitment.model.service.InterviewService;
import team.ojt7.recruitment.model.service.ReportService;
import team.ojt7.recruitment.model.service.VacancyService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ApplicantRepo applicantRepo;
	
	@Autowired
	private VacancyService vacancyService;
	
	@Autowired
	private ApplicantService applicantService;
	
	@Autowired
	private InterviewService interviewService;

	@Override
	public List<TopRecruitmentResourceReportDto> searchTopRecruitmentResources() {
		
		List<ApplicantDto> applicants = ApplicantDto.ofList( applicantRepo.searchStatusAndResources(Status.HIRED));

		List<TopRecruitmentResourceReportDto> topRecruitmentResourceReportDtos = new ArrayList<>();
				
		
		Map<RecruitmentResourceDto,List<ApplicantDto>> map = applicants.stream().collect(Collectors.groupingBy(ApplicantDto::getRecruitmentResource));
		
		for(Entry<RecruitmentResourceDto, List<ApplicantDto>> app:map.entrySet()) {
			TopRecruitmentResourceReportDto topRecResourceReportDtos = new TopRecruitmentResourceReportDto();
			
			topRecResourceReportDtos.setCount(app.getValue().size());
			topRecResourceReportDtos.setRecruitmentResource(app.getKey());
			
			Map<PositionDto, Integer> positionsMap = app.getValue().stream().collect(Collectors.groupingBy(a -> a.getRequirePosition().getPosition(),Collectors.summingInt(a ->1)));
			List<Entry<PositionDto,Integer>>entry=new ArrayList<>(positionsMap.entrySet());
			entry.sort(
					(t1,t2) -> (int) (t2.getValue() - t1.getValue()));
			Map<PositionDto,Integer>sorted=new LinkedHashMap<>();
			for(Entry<PositionDto,Integer> e:entry) {
				sorted.put(e.getKey(), e.getValue());
			}
			topRecResourceReportDtos.setPositions(sorted);
			
			topRecruitmentResourceReportDtos.add(topRecResourceReportDtos);
		}
			
		topRecruitmentResourceReportDtos.sort(
				(t1, t2) -> (int) (t2.getCount() - t1.getCount())
				);
		
		return topRecruitmentResourceReportDtos;
	}


	@Override
	public List<TopRecruitmentResourceByPositionDto> searchTopRecruitmentResourcesByPosition() {
		
		List<ApplicantDto> applicants=ApplicantDto.ofList(applicantRepo.searchStatusAndResources(Status.HIRED));
		List<TopRecruitmentResourceByPositionDto> topRecruitmentResourceByPosition=new ArrayList<>();
		Map<PositionDto,List<ApplicantDto>> map = applicants.stream().collect(Collectors.groupingBy(a->a.getRequirePosition().getPosition()));
		for(Entry<PositionDto,List<ApplicantDto>> app:map.entrySet()) {
			TopRecruitmentResourceByPositionDto dto=new TopRecruitmentResourceByPositionDto();
		dto.setPosition(app.getKey());
		dto.setCount(app.getValue().size());
		Map<RecruitmentResourceDto,Integer>recruitment=app.getValue().stream().collect(Collectors.groupingBy(a ->a.getRecruitmentResource(),Collectors.summingInt(a ->1)));
		List<Entry<RecruitmentResourceDto,Integer>>list=new ArrayList<>(recruitment.entrySet());
		list.sort(
				(t1,t2) -> t2.getValue()-t1.getValue());
		Map<RecruitmentResourceDto,Integer>sorted=new LinkedHashMap<>();
		for(Entry<RecruitmentResourceDto,Integer> e:list) {
			sorted.put(e.getKey(), e.getValue());
		}
		dto.setRecruitmentResources(sorted);
		topRecruitmentResourceByPosition.add(dto);
		}
		topRecruitmentResourceByPosition.sort(
				(t1, t2) -> (int) (t2.getCount() - t1.getCount())
				);
		return topRecruitmentResourceByPosition;
	}


	@Override
	public Map<PositionDto, CandidateCountInfo> searchDemandPositionReport(LocalDate dateFrom, LocalDate dateTo) {
		List<VacancyDto> vacancies = vacancyService.findByCreatedDateRange(dateFrom, dateTo);
		
		var appliedApplicants = applicantService.findByCreatedDateRange(dateFrom, dateTo).stream().collect(Collectors.groupingBy(
					a -> a.getRequirePosition().getPosition(),
					Collectors.counting()
				));
		
		var hiredApplicants = applicantService.findByHiredDateRange(dateFrom, dateTo).stream().filter(a -> a.getStatus() == Status.HIRED).collect(Collectors.groupingBy(
				a -> a.getRequirePosition().getPosition(),
				Collectors.counting()
			));
		
		Map<PositionDto, CandidateCountInfo> data = vacancies.stream().flatMap(v -> v.getRequirePositions().stream())
			.collect(Collectors.groupingBy(
					RequirePositionDto::getPosition,
					Collectors.reducing(new CandidateCountInfo(), i -> {
						CandidateCountInfo info = new CandidateCountInfo();
						info.setPost((long)i.getCount());
						info.setApplied(appliedApplicants.get(i.getPosition()));
						info.setHired(hiredApplicants.get(i.getPosition()));
						return info;
					}, (i1, i2) -> {
						if (i1 != null && i1.getPost() != null) {
							i2.setPost(i2.getPost() + i1.getPost());
						}
						return i2;
					})
			)
		);
		List<Entry<PositionDto, CandidateCountInfo>> dataList = new ArrayList<>(data.entrySet());
		dataList.sort((d1, d2) -> d2.getValue().getPercentage() - d1.getValue().getPercentage());
		Map<PositionDto, CandidateCountInfo> finalData = new LinkedHashMap<>();
		for (var d : dataList) {
			finalData.put(d.getKey(), d.getValue());
		}
		return finalData;
	}


	@Override
	public Map<InterviewNameDto, InterviewStageInfoDto> searchInterviewStageInfoReport(LocalDate dateFrom, LocalDate dateTo, String sort) {
		sort = StringUtils.hasLength(sort) ? sort : "theMostPassed";
		List<InterviewDto> interviews = interviewService.findCompletedInterivews(dateFrom, dateTo);
		
		Map<InterviewNameDto, InterviewStageInfoDto> result = interviews.stream().collect(
					Collectors.groupingBy(
						i -> i.getInterviewName(),
						Collectors.reducing(
							new InterviewStageInfoDto(),
							i -> {
								InterviewStageInfoDto iDto = new InterviewStageInfoDto();
								
								Map<PositionDto, InterviewStageInfoByPosition> map = new HashMap<>();
								InterviewStageInfoByPosition iDtoByPosition = new InterviewStageInfoByPosition();
								iDtoByPosition.setTotal(1L);
								
								iDto.setTotal(1L);
								if (i.getStatus() == Interview.Status.PASSED) {
									iDto.setPassed(1L);
									iDtoByPosition.setPassed(1L);
								} else {
									iDto.setFailed(1L);
									iDtoByPosition.setFailed(1L);
								}
								
								map.put(i.getApplicant().getRequirePosition().getPosition(), iDtoByPosition);
								iDto.setStagesByPosition(map);
								return iDto;
							},
							(i1 , i2) -> {
								if (i1 != null) {
									i2.setTotal(i1.getTotal() + i2.getTotal());
									i2.setPassed(i1.getPassed() + i2.getPassed());
									i2.setFailed(i1.getFailed() + i2.getFailed());
									
									if (i1.getStagesByPosition() != null) {
										Map<PositionDto, InterviewStageInfoByPosition> map1 = i1.getStagesByPosition();
										Map<PositionDto, InterviewStageInfoByPosition> map2 = i2.getStagesByPosition();
										
										for (Entry<PositionDto, InterviewStageInfoByPosition> entry : map1.entrySet()) {
											if (map2.get(entry.getKey()) == null) {
												map2.put(entry.getKey(), entry.getValue());
											} else {
												InterviewStageInfoByPosition is1 = entry.getValue();
												InterviewStageInfoByPosition is2 = map2.get(entry.getKey());
												is2.setTotal(is2.getTotal() + is1.getTotal());
												is2.setPassed(is2.getPassed() + is1.getPassed());
												is2.setFailed(is2.getFailed() + is1.getFailed());
											}
										}
									}
									
									
									
								}
								return i2;
							}
						)
					)
				);
		
		List<Entry<InterviewNameDto, InterviewStageInfoDto>> resultList = new ArrayList<>(result.entrySet());
		if ("theMostPassed".equals(sort)) {
			resultList.sort(
					(i1, i2) -> i2.getValue().getPercentage() - i1.getValue().getPercentage()
				);
		} else if ("theLeastPassed".equals(sort)) {
			resultList.sort(
					(i1, i2) -> i1.getValue().getPercentage() - i2.getValue().getPercentage()
				);
		}
		Map<InterviewNameDto, InterviewStageInfoDto> resultMap = new LinkedHashMap<>();
		for (Entry<InterviewNameDto, InterviewStageInfoDto> entry : resultList) {
			resultMap.put(entry.getKey(), entry.getValue());
		}
		
		return resultMap;
	}

}
