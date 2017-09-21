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
package com.meltmedia.dropwizard.jest.junit;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class JestClientRule
  implements TestRule {
  
  String uri;
  JestClient client;
  Function<HttpClientConfig.Builder,HttpClientConfig.Builder> httpClientSettings = settings->settings;

  public JestClientRule( String uri ) {
    this.uri = uri;
  }
  
  public JestClientRule withHttpSettings( Function<HttpClientConfig.Builder, HttpClientConfig.Builder> settings) {
    httpClientSettings = httpClientSettings
      .andThen(settings);
    return this;
  }
  
  @Override
  public Statement apply( final Statement base, final Description description ) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        JestClientFactory factory = new JestClientFactory();
        HttpClientConfig.Builder builder = new HttpClientConfig
          .Builder(uri)
          .multiThreaded(true);
        httpClientSettings.apply(builder);
        factory.setHttpClientConfig(builder.build());
        client = factory.getObject();
        
        try {
          base.evaluate();
        }
        finally {
          client = null;
        }
      }
    };
  }

  public JestClient getClient() {
    return client;
  }
}
