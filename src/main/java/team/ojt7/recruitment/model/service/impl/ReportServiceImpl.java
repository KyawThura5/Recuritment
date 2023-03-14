package team.ojt7.recruitment.model.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceByPositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceReportDto;
import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;
import team.ojt7.recruitment.model.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ApplicantRepo applicantRepo;
	
	@Autowired
	RecruitmentResourceRepo recruitmentResourceRepo;
	

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

}
