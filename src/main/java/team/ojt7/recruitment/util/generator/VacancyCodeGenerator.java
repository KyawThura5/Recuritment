package team.ojt7.recruitment.util.generator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class VacancyCodeGenerator implements Generator<Long, String> {

	@Override
	public String generate(Long p) {
		return "DAT_VACANCY_%s_%04d".formatted(LocalDate.now(), p);
	}

}
