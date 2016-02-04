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

import io.searchbox.action.AbstractMultiIndexActionBuilder;
import io.searchbox.action.GenericResultAbstractAction;

/**
 * @author cihat keser
 */
public class DeleteAlias extends GenericResultAbstractAction {

    private Object alias;

    private DeleteAlias(Builder builder) {
        super(builder);
        this.alias = builder.alias;
        setURI(buildURI());
    }

    @Override
    public String getRestMethodName() {
        return "DELETE";
    }

    @Override
    protected String buildURI() {
        StringBuilder sb = new StringBuilder();
        sb.append("/_aliases/").append(alias);
        return sb.toString();
    }

    public static class Builder extends AbstractMultiIndexActionBuilder<DeleteAlias, Builder> {

      private Object alias;
      public Builder( String alias ) {
        this.alias = alias;
      }
        @Override
        public DeleteAlias build() {
            return new DeleteAlias(this);
        }
    }
}