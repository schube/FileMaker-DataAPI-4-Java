# FileMaker-DataAPI-4-Java

Java Implementation for FileMaker Data REST API at https://help.claris.com/en/data-api-guide/

I am using this lib in my own projects and I only implemented what I need by myself.
Also, the API is subject to change!

# Contributions

Contributions are welcome!

# Usage

Add this dependency to your maven POM

    <dependency>
      <groupId>com.schubec.libs</groupId>
      <artifactId>filemaker-data-api-4-java</artifactId>
      <version>0.0.7-SNAPSHOT</version>
    </dependency>

The artifacts are published to s01.oss.sonatype, so you have to add this repository:

	<repositories>
    	<repository>
        	<id>oss-sonatype</id>
	        <name>oss-sonatype</name>
    	    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
        	<snapshots>
	            <enabled>true</enabled>
    	    </snapshots>
	    </repository>
	</repositories>

# FileMaker Hosting

* Please consider hosting your Claris FileMaker database at [fm-server.net](http://fm-server.net) to support this open source library.

# Internal

## Note to self

### Build

    mvn package -Dmaven.test.skip=true

### Publish Snapshot

    export GPG_TTY=$(tty)
    mvn clean source:jar deploy -Dmaven.test.skip=true -P ossrh