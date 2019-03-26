# SharieAPI

[![Coverage Status](https://coveralls.io/repos/github/glo2003/glo-2003-h19-equipe-16/badge.svg?branch=master&t=f30cuN)](https://coveralls.io/github/glo2003/glo-2003-h19-equipe-16?branch=master)
//[![Heroku](https://heroku-badge.herokuapp.com/?app=thawing-reef-71512&svg=1)](https://thawing-reef-71512.herokuapp.com/)//

Built with [spark](http://sparkjava.com/) framework for simplicity

## Installation (IntelliJ with Maven on Windows)

1. Download the following
    * IntelliJ : https://www.jetbrains.com/idea/download/#section=windows
    * Java SDK 1.6 : https://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase6-419409.html
    * Java SDK 1.8 : https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    * Maven : http://mirror.dsrg.utoronto.ca/apache/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.zip
2. Install IntelliJ
3. Install Java SDK 1.6 and Java SDK 1.8 and remember the installation paths
4. Install Maven
    1. Extract the files from the Maven download to a folder of your choice(remember the path)
    2. Press Win + R
    3. In the Run box type : rundll32.exe sysdm.cpl,EditEnvironmentVariables
and Press Control + Shift before running a program and it'll run as admin
    4. Now edit the Path variable from the System variables list
    5. In the edit environment variable window click browse and select the file from where you extracted Maven, then Ok
5. Add JAVA_HOME environment variable(Maven needs it)
    1. If the environment variable window is close restart step 4.2 and 4.3
    2. Press New under the system variables list
    3. In Variable name type : JAVA_HOME, then press Browse Directory and select the folder named jdk1.8.#_###(use path of step 3 for Java SDK 1.8), then Ok
6. Clone the project with this command : git clone --recursive https://github.com/glo2003/glo-2003-h19-equipe-16
    1. Open the cloned project in IntelliJ
    2. In IntelliJ press Ctrl + Shift + Alt + s
    3. In the SDK's menu press the + then +JDK and add the folder named jdk1.6.#_###(use path of step 3 for Java SDK 1.6), then Ok
8. Test the installation
    1. In IntelliJ use the terminal with the following command : mvn clean install
    2. In the same terminal type : mvn exec:java
    3. The server is started you can now use a web browser with the url localhost:9090 ans see the following: Sharie API
9.  User Interface (Optional)
    1. Download Elm from here and install it : https://guide.elm-lang.org/install.html
    2. From UI folder use the following command : elm reactor
    3. The UI is started you can now use a web browser with the url localhost:8000/src/Main.elm ans see the UI for this project.

## Maven Run and Coverage configurations

1. Install IntelliJ Maven plugin
    1. Go to `File > Settings > Plugins > Installed`, search for `Maven Integration`, check it and restart IntelliJ
    2. Once IntelliJ has restarted, click on `Enable auto-imports` when the popup appears
    3. Click on `Add configuration` between the `build` and the `run` icons
    4. Click on `+ > Maven`
        1. For the (test and) `Run` configuration, write `clean install exec:java` inside the field `Command line`
        2. For the (test) `Coverage` configuration, write `clean test jacoco:report coveralls:report` inside the field `Command line`

> Note : test coverage results will be available at https://coveralls.io/github/glo2003/glo-2003-h19-equipe-16

## Building

```bash
mvn clean install
```

## Running

```bash
mvn exec:java
```

## Generating the documentation

1. Install [Node](https://nodejs.org) and [Yarn](https://yarnpkg.com)
2. cd to project directory and run `yarn install`
3. To generate and open the documentation, simply run `yarn doc`

## Dependencies and interesing doc:

- [Jackson](http://wiki.fasterxml.com/JacksonHome) [![Build Status](https://travis-ci.org/FasterXML/jackson-core.svg?branch=master)](https://travis-ci.org/FasterXML/jackson-core)
- [Jackson-annotations](https://github.com/FasterXML/jackson-annotations) [![Build Status](https://travis-ci.org/FasterXML/jackson-annotations.svg?branch=master)](https://travis-ci.org/FasterXML/jackson-annotations)
- [SparkJava](http://sparkjava.com) [![Build Status](https://travis-ci.org/perwendel/spark.svg?branch=master)](https://travis-ci.org/perwendel/spark)
- [Javaslang](http://javaslang.com/) - better java 8 (this is really useful) (name changed to vavr...?) [![Build Status](https://travis-ci.org/vavr-io/vavr.svg?branch=master)](https://travis-ci.org/vavr-io/vavr)

For testing 

- [Junit](http://junit.org/)
- [Truth](https://google.github.io/truth/) [![Build Status](https://travis-ci.org/google/truth.svg?branch=master)](https://travis-ci.org/google/truth) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.rsql/rsql-parser/badge.svg)](https://search.maven.org/artifact/com.google.truth/truth)
- [REST-assured](http://rest-assured.io/) [![Build Status](https://travis-ci.org/rest-assured/rest-assured.svg?branch=master)](https://travis-ci.org/rest-assured/rest-assured) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.rsql/rsql-parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.rest-assured/rest-assured)
- [Hamcrest](http://hamcrest.org/JavaHamcrest/javadoc/2.1/) [![Build Status](https://travis-ci.org/hamcrest/JavaHamcrest.svg?branch=master)](https://travis-ci.org/hamcrest/JavaHamcrest) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.rsql/rsql-parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/g:org.hamcrest/org.hamcrest)
