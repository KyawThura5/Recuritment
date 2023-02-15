package team.ojt7.recruitment.util.generator;

import org.springframework.stereotype.Component;

@Component
public class RecruitmentResourceCodeGenerator implements Generator<Long, String> {

	@Override
	public String generate(Long p) {
		return "DAT_RESOURCE_%04d".formatted(p);
	}

}
