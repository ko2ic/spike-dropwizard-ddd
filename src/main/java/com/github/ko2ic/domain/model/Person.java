package com.github.ko2ic.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "people")
@NamedQueries({ @NamedQuery(name = "com.github.ko2ic.core.Person.findAll", query = "SELECT p FROM Person p"),
// @NamedQuery(name = "com.github.ko2ic.core.Person.findById", query =
// "SELECT p FROM Person p WHERE p.id = :id")
})
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "fullName", nullable = false)
	private String fullName;

	@Column(name = "jobTitle", nullable = false)
	private String jobTitle;

	public Person() {

	}

	public Person(long id, String fullName, String jobTitle) {
		this.id = id;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
}
