package com.meltmedia.dropwizard.jest;

import io.dropwizard.lifecycle.Managed;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class JestManager implements Managed {
  
  public JestManager( JestConfiguration configuration ) {
    this.configuration = configuration;
  }

  JestConfiguration configuration;
  
  protected JestClient client;
  
  public JestClient getClient() {
    return client;
  }

  @Override
  public void start() throws Exception {
    JestClientFactory factory = new JestClientFactory();
    factory.setHttpClientConfig(new HttpClientConfig
                           .Builder(configuration.getUri())
                           .multiThreaded(true)
                           .connTimeout(configuration.getConnectionTimeout())
                           .readTimeout(configuration.getReadTimeout())
                           // giving a little head room on connection pool.  The single max concurrent executions parameter
                           // needs to be modified.
                           .maxTotalConnection(configuration.getMaxTotalConnection())
                           .defaultMaxTotalConnectionPerRoute(configuration.getDefaultMaxTotalConnectionPerRoute())
                           .build());
    client = factory.getObject();
  }

  @Override
  public void stop() throws Exception {
    client.shutdownClient();
  }

}
