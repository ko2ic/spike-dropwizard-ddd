package com.github.ko2ic.interfaces.resources;

import io.dropwizard.jersey.params.LongParam;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.ko2ic.application.facade.PeopleFacade;
import com.github.ko2ic.domain.model.Person;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {

	private final PeopleFacade facade;

	@Inject
	public PeopleResource(PeopleFacade facade) {
		this.facade = facade;
	}

	@POST
	// @UnitOfWork
	public Person createPerson(Person person) {
		try {
			Person result = facade.create(person);
			return result;
		} catch (Exception e) {
			return new Person();
		}
	}

	@GET
	// @UnitOfWork
	public List<Person> listPeople() {
		return facade.findAll();
	}

	@GET
	// @UnitOfWork
	@Path("/{personId}")
	public Person getPerson(@PathParam("personId") LongParam personId) {
		final Optional<Person> person = facade.findById(personId.get());
		if (!person.isPresent()) {
			throw new NotFoundException("{status:notfound}");
		}
		return person.get();
	}
}
