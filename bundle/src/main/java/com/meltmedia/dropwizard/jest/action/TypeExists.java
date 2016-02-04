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

public class TypeExists extends GenericResultAbstractAction {

  public TypeExists( Builder builder ) {
    super(builder);
    setURI(buildURI());
  }

  @Override
  public String getRestMethodName() {
    return "HEAD";
  }
  
  public static class Builder extends AbstractMultiTypeActionBuilder<TypeExists, Builder> {
    public Builder(String index, String type) {
      this.addIndex(index);
      this.addType(type);
    }

    @Override
    public TypeExists build() {
      return new TypeExists(this);
    }
  }

}
