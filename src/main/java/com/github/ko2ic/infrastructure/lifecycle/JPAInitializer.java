package com.github.ko2ic.infrastructure.lifecycle;

import io.dropwizard.lifecycle.Managed;

import javax.inject.Singleton;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

@Singleton
public class JPAInitializer implements Managed {

	private final PersistService service;

	@Inject
	public JPAInitializer(final PersistService service) {
		this.service = service;
	}

	@Override
	public void start() throws Exception {
		service.start();
	}

	@Override
	public void stop() throws Exception {
		service.stop();
	}
}
