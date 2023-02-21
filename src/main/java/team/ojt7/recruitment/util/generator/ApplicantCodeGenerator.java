package team.ojt7.recruitment.util.generator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class ApplicantCodeGenerator implements Generator<Long, String>{
	
	@Override
	public String generate(Long p) {
		return "DAT_APPLICANT_%s_%04d".formatted(LocalDate.now(), p);
	}

}
