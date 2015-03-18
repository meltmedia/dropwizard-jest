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

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.cluster.Health;

import java.util.function.Supplier;

import com.codahale.metrics.health.HealthCheck;

public class JestHealthCheck extends HealthCheck {

  protected Supplier<JestClient> clientSupplier;

  public JestHealthCheck(Supplier<JestClient> clientSupplier) {
    this.clientSupplier = clientSupplier;
  }

  @Override
  protected Result check() throws Exception {
    JestResult result = clientSupplier.get().execute(new Health.Builder().build());
    
    if( !result.isSucceeded() ) {
      return Result.unhealthy("cannot get cluster health information.");
    }
    
    // for now, if we can reach the cluster, then the app is healthy.
    return Result.healthy();
  }
}
