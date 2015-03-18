/**
 * Copyright (C) 2014 meltmedia (christian.trimble@meltmedia.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meltmedia.dropwizard.jest;

import java.util.function.Supplier;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.searchbox.client.JestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JestBundle<C extends Configuration> implements ConfiguredBundle<C> {
  public static Logger log = LoggerFactory.getLogger(JestBundle.class);
  public static interface ConfigurationAccessor<C extends Configuration> {
    public JestConfiguration configuration(C configuration);
  }
  
  public static class Builder<C extends Configuration> {
    protected ConfigurationAccessor<C> configuraitonAccessor;
    protected String healthCheckName = "elasticsearch";
    
    public Builder<C> withConfiguraiton( ConfigurationAccessor<C> configuraitonAccessor ) {
      this.configuraitonAccessor = configuraitonAccessor;
      return this;
    }
    
    public Builder<C> withHealthCheckName( String healthCheckName ) {
      this.healthCheckName = healthCheckName;
      return this;
    }
    
    public JestBundle<C> build() {
      if( configuraitonAccessor == null ) {
        throw new IllegalArgumentException("configuration accessor is required.");
      }
      return new JestBundle<C>(configuraitonAccessor, healthCheckName);
    }
  }
  
  public static <C extends Configuration> Builder<C> builder() {
    return new Builder<C>();
  }
  
  protected ConfigurationAccessor<C> configuraitonAccessor;
  protected String healthCheckName;
  protected JestConfiguration jestConfiguration;
  protected JestHealthCheck healthCheck;
  protected JestManager manager;
  protected Supplier<JestClient> clientSupplier;

  public JestBundle(ConfigurationAccessor<C> configuraitonAccessor, String healthCheckName) {
    this.configuraitonAccessor = configuraitonAccessor;
    this.healthCheckName = healthCheckName;
  }
    
  public Supplier<JestClient> getClientSupplier() {
    return clientSupplier;
  }

  public JestManager getManager() {
    return manager;
  }
  
  public JestHealthCheck getHealthCheck() {
    return healthCheck;
  }
  
  @Override
  public void run(C configuration, Environment environment) throws Exception {
    jestConfiguration = configuraitonAccessor.configuration(configuration);
    manager = new JestManager(jestConfiguration);
    clientSupplier = manager::getClient;
    healthCheck = new JestHealthCheck(clientSupplier);
    
    environment.lifecycle().manage(manager);
    environment.healthChecks().register(healthCheckName, healthCheck);
  }

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
  }
  
}
