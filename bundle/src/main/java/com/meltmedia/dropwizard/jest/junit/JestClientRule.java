package com.meltmedia.dropwizard.jest.junit;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class JestClientRule
  implements TestRule {
  
  String uri;
  JestClient client;

  public JestClientRule( String uri ) {
    this.uri = uri;
  }
  
  @Override
  public Statement apply( final Statement base, final Description description ) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                               .Builder(uri)
                               .multiThreaded(true)
                               .build());
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
