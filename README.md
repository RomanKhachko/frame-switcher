# Frame-switcher

Contains a library for supporting automatically frame switching inside page (or any other abstractions) classes. It's very easy to use with page object pattern.

## usage
In order to use project you need:
* add FrameSwitcher dependency in your pom.xml:
  ```xml 
<dependency>
    <groupId>com.github.RomanKhachko.fsp</groupId>
    <artifactId>FSP-main</artifactId>
    <version>2.0.0</version>
</dependency>
```
* add following plugin to a build section
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
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
* add property java.version (optional), or use constant values in proper plaseces.
  ```xml 
<properties>
    <java.version>1.8</java.version>
</properties>
```
    *Please note, you can specify any version of java here, but not lower than 1.6*

## version 
2.0.0
## examples 
Please, take a look at a [sample project](/fsp_examples) with examples of usage.
## contact
romankhachko@gmail.com