package team.ojt7.recruitment.util.generator;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class UserCodeGenerator implements Generator<Long, String> {

	@Override
	public String generate(Long id) {
		Objects.requireNonNull(id, "Id must not be null.");
		return "emp%04d".formatted(id);
	}

}
