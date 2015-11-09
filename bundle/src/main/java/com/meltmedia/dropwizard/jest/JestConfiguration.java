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

import java.util.Arrays;
import java.util.List;

public class JestConfiguration {
  protected String clusterName;
  protected List<String> servers;
  protected int connectionTimeout = 30000;
  protected int readTimeout = 30000;
  protected int maxTotalConnection = 1;
  protected int defaultMaxTotalConnectionPerRoute = 1;

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName( String clusterName ) {
    this.clusterName = clusterName;
  }
  
  public JestConfiguration withClusterName( String clusterName ) {
    this.clusterName = clusterName;
    return this;
  }
  
  /**
   * @deprecated Use {@link #getServers()} instead.
   */
  @Deprecated
  public String getUri() {
	return servers == null || servers.isEmpty() ? null : servers.get(0);
  }

  /**
   * @deprecated Use {@link #setServers(List)} instead
   */
  @Deprecated
  public void setUri( String uri ) {
    this.servers = Arrays.asList(uri);
  }
  
  /**
   * @deprecated Use {@link #withServers(String...)} instead
   */
  @Deprecated
  public JestConfiguration withUri( String uri ) {
	this.servers = Arrays.asList(uri);
    return this;
  }

  public List<String> getServers() {
    return servers;
  }

  public void setServers( List<String> servers ) {
    this.servers = servers;
  }
  
  public JestConfiguration withServers( String... servers ) {
    this.servers = Arrays.asList(servers);
    return this;
  }
  
  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout( int connectionTimeout ) {
    this.connectionTimeout = connectionTimeout;
  }

  public JestConfiguration withConnectionTimeout( int connectionTimeout ) {
    this.connectionTimeout = connectionTimeout;
    return this;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout( int readTimeout ) {
    this.readTimeout = readTimeout;
  }

  public JestConfiguration withReadTimeout( int readTimeout ) {
    this.readTimeout = readTimeout;
    return this;
  }

  public int getMaxTotalConnection() {
    return maxTotalConnection;
  }

  public void setMaxTotalConnection( int maxTotalConnection ) {
    this.maxTotalConnection = maxTotalConnection;
  }
  
  public JestConfiguration withMaxTotalConnection( int maxTotalConnection ) {
    this.maxTotalConnection = maxTotalConnection;
    return this;
  }

  public int getDefaultMaxTotalConnectionPerRoute() {
    return defaultMaxTotalConnectionPerRoute;
  }

  public void setDefaultMaxTotalConnectionPerRoute( int defaultMaxTotalConnectionPerRoute ) {
    this.defaultMaxTotalConnectionPerRoute = defaultMaxTotalConnectionPerRoute;
  }

  public JestConfiguration withDefaultMaxTotalConnectionPerRoute( int defaultMaxTotalConnectionPerRoute ) {
    this.defaultMaxTotalConnectionPerRoute = defaultMaxTotalConnectionPerRoute;
    return this;
  }

}
