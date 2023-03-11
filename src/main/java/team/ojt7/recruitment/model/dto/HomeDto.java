package team.ojt7.recruitment.model.dto;

import java.util.HashMap;
import java.util.Map;

public class HomeDto {

	private long jobOpeningsCount;
	private long jobExpiringsCount;
	private long notStartedInterviewsCount;
	private long newApplicantsCount;
	private Map<RecruitmentResourceDto, Long> topRecruitmentResources = new HashMap<>();

	public long getJobOpeningsCount() {
		return jobOpeningsCount;
	}

	public void setJobOpeningsCount(long jobOpeningsCount) {
		this.jobOpeningsCount = jobOpeningsCount;
	}

	public long getJobExpiringsCount() {
		return jobExpiringsCount;
	}

	public void setJobExpiringsCount(long jobExpiringsCount) {
		this.jobExpiringsCount = jobExpiringsCount;
	}

	public long getNotStartedInterviewsCount() {
		return notStartedInterviewsCount;
	}

	public void setNotStartedInterviewsCount(long notStartedInterviewsCount) {
		this.notStartedInterviewsCount = notStartedInterviewsCount;
	}

	public long getNewApplicantsCount() {
		return newApplicantsCount;
	}

	public void setNewApplicantsCount(long newApplicantsCount) {
		this.newApplicantsCount = newApplicantsCount;
	}

	public Map<RecruitmentResourceDto, Long> getTopRecruitmentResources() {
		return topRecruitmentResources;
	}

	public void setTopRecruitmentResources(Map<RecruitmentResourceDto, Long> topRecruitmentResources) {
		this.topRecruitmentResources = topRecruitmentResources;
	}

}
