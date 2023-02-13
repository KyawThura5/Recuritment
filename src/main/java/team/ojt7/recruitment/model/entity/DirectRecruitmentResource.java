package team.ojt7.recruitment.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "direct_recruitment_resource")
@DiscriminatorValue(value = "DirectRecruitmentResource")
public class DirectRecruitmentResource extends RecruitmentResource {

	private static final long serialVersionUID = 1L;

	
}