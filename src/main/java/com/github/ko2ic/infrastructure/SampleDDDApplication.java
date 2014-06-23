package com.github.ko2ic.infrastructure;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.Properties;

import com.github.ko2ic.domain.model.Person;
import com.github.ko2ic.infrastructure.lifecycle.JPAInitializer;
import com.github.ko2ic.interfaces.resources.PeopleResource;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;

public class SampleDDDApplication extends Application<SampleDDDConfiguration> {
	public static void main(String[] args) throws Exception {
		new SampleDDDApplication().run(args);
	}

	@Override
	public String getName() {
		return "hello-ddd";
	}

	private final HibernateBundle<SampleDDDConfiguration> hibernateBundle = new HibernateBundle<SampleDDDConfiguration>(
			Person.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(
				SampleDDDConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(Bootstrap<SampleDDDConfiguration> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<SampleDDDConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(
					SampleDDDConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});

		bootstrap.addBundle(hibernateBundle);
	}

	@Override
	public void run(SampleDDDConfiguration configuration,
			Environment environment) throws ClassNotFoundException {
		Injector injector = createInjector(configuration);
		environment.jersey().register(
				injector.getInstance(PeopleResource.class));

		environment.lifecycle().manage(
				injector.getInstance(JPAInitializer.class));
	}

	private Injector createInjector(final SampleDDDConfiguration conf) {
		return Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(JPAInitializer.class).asEagerSingleton();
				bind(SampleDDDConfiguration.class).toInstance(conf);
				bind(DataSourceFactory.class).toInstance(
						conf.getDataSourceFactory());
			}
		}, createJpaPersistModule(conf.getDataSourceFactory()));
	}

	private JpaPersistModule createJpaPersistModule(DataSourceFactory conf) {
		Properties props = new Properties();
		props.put("javax.persistence.jdbc.url", conf.getUrl());
		props.put("javax.persistence.jdbc.user", conf.getUser());
		props.put("javax.persistence.jdbc.password", conf.getPassword());
		props.put("javax.persistence.jdbc.driver", conf.getDriverClass());
		JpaPersistModule jpaModule = new JpaPersistModule("Default");
		jpaModule.properties(props);
		return jpaModule;
	}
}
