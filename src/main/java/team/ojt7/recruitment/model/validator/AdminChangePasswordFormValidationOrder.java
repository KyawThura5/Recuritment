package team.ojt7.recruitment.model.validator;

import javax.validation.GroupSequence;

@GroupSequence(value = {
	NotNullGroup.class, NotBlankGroup.class, PatternGroup.class, SizeGroup.class
})
public interface AdminChangePasswordFormValidationOrder {

}
