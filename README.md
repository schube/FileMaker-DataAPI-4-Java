# FileMaker-DataAPI-4-Java

Java Implementation for FileMaker Data REST API at https://help.claris.com/en/data-api-guide/

I am using this lib in my own projects and I only implemented what I need by myself.
Also, the API is subject to change!

# Contributions

Contributions are welcome!


# Internal

## Note to self

### Build

    mvn package -Dmaven.test.skip=true

### Publish Snapshot

    mvn clean source:jar deploy -Dmaven.test.skip=true