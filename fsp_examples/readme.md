# compilation and running
Please note, you need to compile with maven before usage.
Choose any phase you want, however, be aware ```mvn compile``` is the lowest phase to build project properly.

[more about maven build lifecycle](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
# running in IDE
In order to apply this goal automatically before running IDE, please edit run configuration. You need to choose run maven goal.

# using library in your tests
If you run tests by using any test plugin (e.g. Surefire or Failsafe) and frameSwitcher library is used directly in test project, you need to add a goal 'test-compile' to your execution section.
Please check an example

 ```xml
 <build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>aspectj-maven-plugin</artifactId>
            <version>1.7</version>

            <configuration>
                <source>${java.version}</source>
                <target>${java.version}</target>
                <complianceLevel>${java.version}</complianceLevel>
                <aspectLibraries>
                    <aspectLibrary>
                        <groupId>com.github.RomanKhachko.fsp</groupId>
                        <artifactId>FSP-main</artifactId>
                    </aspectLibrary>
                </aspectLibraries>
            </configuration>

            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                        <goal>test-compile</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```