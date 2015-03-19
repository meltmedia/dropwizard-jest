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

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("/")
public class RootResource {
  public static Logger log = LoggerFactory.getLogger(RootResource.class);

  private Supplier<JestClient> clientSupplier;
  private ObjectMapper mapper;

  public RootResource(Supplier<JestClient> clientSupplier) {
    this.clientSupplier = clientSupplier;
  }

  @Path("{indexName}")
  public IndexResource index(@PathParam("indexName") String indexName) {
    return new IndexResource(indexName);
  }

  public class IndexResource {

    String indexName;

    public IndexResource(String indexName) {
      this.indexName = indexName;
    }

    @Path("{typeName}")
    public TypeResource type(@PathParam("typeName") String typeName) {
      return new TypeResource(indexName, typeName);
    }

    @GET
    public Response putResource(@QueryParam("q") String query) throws JsonProcessingException {
      try {
        JestResult result =
            clientSupplier.get().execute(
                new Search.Builder(new SearchSourceBuilder()
                    .query(QueryBuilders.queryString(query)).toString()).addIndex(indexName)
                    .build());
        if (!result.isSucceeded()) {
          log.error("could not search for resource: " + result.getJsonString());
          return Response.serverError().build();
        }
        List<String> sources =
            StreamSupport
                .stream(result.getJsonObject().get("hits").getAsJsonArray().spliterator(), false)
                .map(d -> d.getAsJsonObject().get("_source").getAsJsonObject().toString())
                .collect(Collectors.toList());
        return Response.ok(result.getJsonString()).build();
      } catch (Exception e) {
        return Response.serverError().build();
      }
    }
  }

  public class TypeResource {

    String indexName;
    String typeName;

    public TypeResource(String indexName, String typeName) {
      this.indexName = indexName;
      this.typeName = typeName;
    }

    @GET
    @Produces("application/json")
    public Response findResource(@QueryParam("q") String query) throws JsonProcessingException {
      try {
        JestResult result =
            clientSupplier.get().execute(
                new Search.Builder(new SearchSourceBuilder()
                    .query(QueryBuilders.queryString(query)).toString()).addIndex(indexName)
                    .addType(typeName).build());
        if (!result.isSucceeded()) {
          log.error("could not search for document: " + result.getJsonString());
          return Response.serverError().build();
        }
        return Response.ok(result.getJsonString()).build();
      } catch (Exception e) {
        log.error("could not put resource", e);
        return Response.serverError().build();
      }
    }

    @Path("{id}")
    public IdResource getIdResource(@PathParam("id") String id) {
      return new IdResource(indexName, typeName, id);
    }
  }

  public class IdResource {
    String indexName;
    String typeName;
    String id;

    public IdResource(String indexName, String typeName, String id) {
      this.indexName = indexName;
      this.typeName = typeName;
      this.id = id;
    }

    @GET
    @Produces("application/json")
    public Response getResource() {
      try {
        JestResult result =
            clientSupplier.get().execute(new Get.Builder(indexName, id).type(typeName).build());

        if (result.isSucceeded()) {
          return Response.ok().entity(result.getJsonObject().get("_source").toString()).build();
        }

        log.error("index document did not succeed:" + result.getJsonString());
        return Response.serverError().build();
      } catch (JsonProcessingException e) {
        log.error("could not serialize resource", e);
        return Response.status(400).build();
      } catch (Exception e) {
        log.error("could not send content to elasticsearch", e);
        return Response.serverError().build();
      }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response putResource(String document) {
      try {
        JestResult result =
            clientSupplier.get().execute(
                new Index.Builder(document).index(indexName).type(typeName).id(id).build());

        if (result.isSucceeded()) {
          return Response.ok().build();
        }

        log.error("index document did not succeed:" + result.getJsonString());
        return Response.serverError().build();
      } catch (JsonProcessingException e) {
        log.error("could not serialize resource", e);
        return Response.status(400).build();
      } catch (Exception e) {
        log.error("could not send content to elasticsearch", e);
        return Response.serverError().build();
      }
    }
  }

}
