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
package com.meltmedia.dropwizard.jest.example.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import io.dropwizard.testing.junit.DropwizardAppRule;

import javax.ws.rs.core.UriBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.meltmedia.dropwizard.jest.example.ExampleApplication;
import com.meltmedia.dropwizard.jest.example.ExampleConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class RootResourceIT {

  @ClassRule
  public static final DropwizardAppRule<ExampleConfiguration> RULE =
      new DropwizardAppRule<ExampleConfiguration>(ExampleApplication.class, "conf/example.yml");

  public static UriBuilder rootPath() {
    return UriBuilder.fromUri(String.format("http://localhost:%d", RULE.getLocalPort()));
  }

  Client client;

  @Before
  public void setUp() {
    client = new Client();
  }

  @After
  public void tearDown() {
    client.destroy();
  }

  @Test
  public void shouldCreateNewDocument() {
    ClientResponse response = putDocument("index/type/id", "{\"name\":\"value\"}");

    assertThat(response.getStatus(), equalTo(200));

    assertThat(getDocument("index/type/id"), equalTo("{\"name\":\"value\"}"));
  }

  public String getDocument(String path) {
    return client.resource(rootPath().path(path).build()).get(String.class);
  }

  public ClientResponse putDocument(String path, String document) {
    return client.resource(rootPath().path(path).build()).entity(document, "application/json")
        .put(ClientResponse.class);
  }
}
