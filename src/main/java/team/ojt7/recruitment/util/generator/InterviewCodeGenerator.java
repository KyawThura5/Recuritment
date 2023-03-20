package team.ojt7.recruitment.util.generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class InterviewCodeGenerator implements Generator<Long, String>{
	public String generate(Long p) {
		return "DAT_INTERVIEW_%s_%04d".formatted(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), p);
	}
}
