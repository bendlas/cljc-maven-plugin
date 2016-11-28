# cljc-maven-plugin

You can use this in your maven project to support older versions of clojure without cljc support.
It uses [cljsee](https://github.com/aengelberg/cljsee/) to cut out the clojurescript parts when distributing for older clojure versions.

# usage

In your `pom.xml`

```
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
        <groupId>net.bendlas</groupId>
        <artifactId>cljc-maven-plugin</artifactId>
        <version>0.1.0</version>
        <executions>
          <execution>
            <phase>generate-sources</phase>
            <goals><goal>split</goal></goals>
          </execution>
        </executions>
      </plugin>
      ...
    </plugins>
    ...
  </build>
  ...
```
