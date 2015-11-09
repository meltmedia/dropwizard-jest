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
                           .Builder(configuration.getServers())
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
