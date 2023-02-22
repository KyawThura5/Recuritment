package team.ojt7.recruitment.model.validator;

import javax.validation.GroupSequence;

@GroupSequence({
	Order1.class, Order2.class, Order3.class
})
public interface DefaultValidationGroupOrder {

}
