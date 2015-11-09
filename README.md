# Dropwizard Jest

A Dropwizard bundle for the Jest Elasticsearch Client.

[![Build Status](https://travis-ci.org/meltmedia/dropwizard-jest.svg)](https://travis-ci.org/meltmedia/dropwizard-jest)

## Usage

### Maven

Releases of this project are available on Maven Central.  You can include the project with this dependency:

```
<dependency>
  <groupId>com.meltmedia.dropwizard</groupId>
  <artifactId>dropwizard-jest</artifactId>
  <version>0.3.0</version>
</dependency>
```

To use SNAPSHOTs of this project, you will need to include the sonatype repository in your POM.

```
<repositories>
    <repository>
        <snapshots>
        <enabled>true</enabled>
        </snapshots>
        <id>sonatype-nexus-snapshots</id>
        <name>Sonatype Nexus Snapshots</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```

You will also need to include the project in your dependencies.

```
<dependency>
  <groupId>com.meltmedia.dropwizard</groupId>
  <artifactId>dropwizard-jest</artifactId>
  <version>0.4.0-SNAPSHOT</version>
</dependency>
```

### Java

Define the JestConfiguraion class somewhere in your applications configuration.

```
import com.meltmedia.dropwizard.jest.JestConfiguration;

...

  @JsonProperty
  protected JestConfiguration elasticsearch;

  public JestConfiguration getElasticsearch() {
    return jest;
  }
```

Then include the bundle in the `initialize` method of your application.

```
import com.meltmedia.dropwizard.jest.JestBundle;

...
protected JestBundle jestBundle;

@Override
public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
  bootstrap.addBundle(jestBundle = JestBundle.<ExampleConfiguration>builder()
    .withConfiguration(ExampleConfiguration::getElasticsearch)
    .build());
}
```

Finally, use the bundle to access the client supplier.

```
@Override
public void run(ExampleConfiguration config, Environment env) throws Exception {
  JestClient client = jestBundle.getClientSupplier().get();
}
```

### Configuration

Add the jest configuraiton block to your applications config.

```
elasticsearch:
  clusterName: elasticsearch
  uri: 'http://localhost:9200'
```

## Building

This project builds with Java 8 and Maven 3.  After cloning the repo, install the bundle from the root of the project.

```
mvn clean install
```

### Integration Tests

Run the build with the `integration-tests` profile.

```
mvn clean install -P integration-tests
```

## Contributing

This project accepts PRs, so feel free to fork the project and send contributions back.

### Formatting

This project contains formatters to help keep the code base consistent.  The formatter will update Java source files and add headers to other files.  When running the formatter, I suggest the following procedure:

1. Make sure any outstanding stages are staged.  This will prevent the formatter from destroying your code.
2. Run `mvn format`, this will format the source and add any missing license headers.
3. If the changes look good and the project still compiles, add the formatting changes to your staged code.

If things go wrong, you can run `git checkout -- .` to drop the formatting changes. 
