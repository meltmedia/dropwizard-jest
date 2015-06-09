package com.meltmedia.dropwizard.jest.guice;

import java.util.function.Supplier;

import io.searchbox.client.JestClient;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.meltmedia.dropwizard.jest.JestBundle;

public class JestModule extends AbstractModule {
  
  JestBundle<?> bundle;

  public JestModule( JestBundle<?> bundle ) {
    this.bundle = bundle;
  }

  @Override
  protected void configure() {
    // all bindings in provider methods.
  }

  @Provides
  @Singleton
  public JestClient provideClient() {
    return bundle.getClientSupplier().get();
  }
  
  @Provides
  @Singleton
  public Supplier<JestClient> provideClientSupplier() {
    return bundle.getClientSupplier();
  }
}