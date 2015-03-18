package com.meltmedia.dropwizard.jest;

public class JestConfiguration {
  protected String clusterName;
  protected String uri;
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

  public String getUri() {
    return uri;
  }

  public void setUri( String uri ) {
    this.uri = uri;
  }
  
  public JestConfiguration withUri( String uri ) {
    this.uri = uri;
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
