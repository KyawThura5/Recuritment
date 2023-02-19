package team.ojt7.recruitment.model.service.impl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.repo.VacancyRepo;
import team.ojt7.recruitment.model.service.VacancyService;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.util.generator.VacancyCodeGenerator;

@Service
public class VacancyServiceImpl implements VacancyService {
	
	@Autowired
	private VacancyRepo vacancyRepo;
	
	@Autowired
	private VacancyCodeGenerator vacancyCodeGenerator;

	@Override
	@Transactional
	public VacancyDto save(Vacancy vacancy) {
        InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		Vacancy savedVacancy=vacancyRepo.save(vacancy);
		return VacancyDto.of(savedVacancy);
	}

	@Override
	@Transactional
	public boolean deleteById(Long id) {
		vacancyRepo.deleteById(id);
		return true;
	}

	@Override
	@Transactional
	public Optional<VacancyDto> findById(Long id) {
		if (id == null) {
			return Optional.ofNullable(null);
		}
		Vacancy vacancy=vacancyRepo.findById(id).orElse(null);
		return Optional.ofNullable(VacancyDto.of(vacancy));
	}

	@Override
	@Transactional
	public VacancyDto generateNewWithCode() {
		Long maxId = vacancyRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		VacancyDto vacancy = new VacancyDto();
		vacancy.setCode(vacancyCodeGenerator.generate(id));
		return vacancy;
	}

	@Override
	@Transactional
	public List<VacancyDto> search(String keyword, Status status, LocalDate dateFrom, LocalDate dateTo) {
		keyword = keyword == null ? "%%" : "%" + keyword + "%";
		List<Vacancy> vacancies=vacancyRepo.search(keyword,status,dateFrom,dateTo);
		return VacancyDto.ofList(vacancies);
	}

}
