package io.wiklandia.batch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SimpleController {

	private final PersonRepository personRepo;

	@GetMapping("test")
	public Page<Person> yo(@PageableDefault Pageable p, Predicate predicate) {
		return personRepo.findAll(predicate, p);
	}

}
