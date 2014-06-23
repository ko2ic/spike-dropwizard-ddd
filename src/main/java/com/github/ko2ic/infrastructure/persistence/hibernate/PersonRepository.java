package com.github.ko2ic.infrastructure.persistence.hibernate;

import java.util.List;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import com.github.ko2ic.domain.model.Person;
import com.google.common.base.Optional;
import com.google.inject.Inject;

//public class PersonDao extends AbstractDAO<Person> {
//
//	@Inject
//	public PersonDao(SessionFactory factory) {
//		super(factory);
//	}
//
//	public Optional<Person> findById(Long id) {
//		return Optional.fromNullable(get(id));
//	}
//
//	public Person create(Person person) {
//		return persist(person);
//	}
//
//	public List<Person> findAll() {
//		return list(namedQuery("com.github.ko2ic.core.Person.findAll"));
//	}
//}

@Singleton
public class PersonRepository {

	private final Provider<EntityManager> entityManager;

	@Inject
	public PersonRepository(Provider<EntityManager> entityManager) {
		this.entityManager = entityManager;
	}

	public Optional<Person> findById(Long id) {
		return Optional
				.fromNullable(entityManager.get().find(Person.class, id));
	}

	public Person create(Person person) {
		entityManager.get().persist(person);
		return person;
	}

	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		return entityManager.get()
				.createNamedQuery("com.github.ko2ic.core.Person.findAll")
				.getResultList();
	}
}