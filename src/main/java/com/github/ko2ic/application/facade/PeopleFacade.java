package com.github.ko2ic.application.facade;

import java.util.List;

import com.github.ko2ic.domain.model.Person;
import com.github.ko2ic.infrastructure.persistence.hibernate.PersonRepository;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class PeopleFacade {

	@Inject
	private PersonRepository dao;

	@Transactional
	public Person create(Person person) {
		// Person temp = new Person();
		// temp.setFullName("aaa");
		// temp.setJobTitle("bbb");
		// dao.create(temp);
		// throw new RuntimeException();
		return dao.create(person);
	}

	public List<Person> findAll() {
		return dao.findAll();
	}

	public Optional<Person> findById(Long id) {
		return dao.findById(id);
	}
}
