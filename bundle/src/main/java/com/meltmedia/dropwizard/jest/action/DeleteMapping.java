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
package com.meltmedia.dropwizard.jest.action;

import io.searchbox.action.AbstractMultiTypeActionBuilder;
import io.searchbox.action.GenericResultAbstractAction;

public class DeleteMapping extends GenericResultAbstractAction {

  private DeleteMapping(Builder builder) {
      super(builder);
      setURI(buildURI());
  }

  @Override
  public String getRestMethodName() {
      return "DELETE";
  }

  @Override
  protected String buildURI() {
      StringBuilder sb = new StringBuilder();
      sb.append(super.buildURI()).append("/_mapping");
      return sb.toString();
  }

  public static class Builder extends AbstractMultiTypeActionBuilder<DeleteMapping, Builder> {
      @Override
      public DeleteMapping build() {
          return new DeleteMapping(this);
      }
  }

}