package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.CandidateCountInfo;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceByPositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceReportDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.service.ApplicantService;
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

}
