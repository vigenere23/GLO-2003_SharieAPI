<img style="width:100%;" src="/github-banner.png">

# starter-kit-java

Java starter kit for the glo2003 laboratory at Ulaval.

Built with [spark](http://sparkjava.com/) framework for simplicity

Starting at java is not easy: [better-java](https://github.com/cxxr/better-java) gives you modern best practices.

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
7. Install the Maven Plugin from IntelliJ and setup configuration
   1. Restart the IDE
   2. Click on "Enable auto-imports" and wait
   3. Click on Add Configuration
   4. Click on "+" and select "maven"
   5. Write "exec:java" as the command line
   6. **You will need to always compile before running, otherwise you won't see your changes.**
8. Test the installation
    1. In IntelliJ use the terminal with the following command : mvn clean install
    2. In the same terminal type : mvn exec:java
    3. The server is started you can now use a web browser with the url localhost:9090 ans see the following: Sharie API
9.  User Interface (Optional)
    1. Download Elm from here and install it : https://guide.elm-lang.org/install.html
    2. From UI folder use the following command : elm reactor
    3. The UI is started you can now use a web browser with the url localhost:8000/src/Main.elm ans see the UI for this project.

## Building

```bash
mvn clean install
```

## Running

```bash
mvn exec:java
```

## Generating the documentation

1. Install Node and Yarn
2. Run `yarn global add apidoc`
3. cd to project directory and run `yarn install`
4. To generate the documentation, simply run `yarn doc`

## Dependencies and interesing doc:

- [Jackson](http://wiki.fasterxml.com/JacksonHome)
- [Jackson-annotations](https://github.com/FasterXML/jackson-annotations)
- [SparkJava](http://sparkjava.com)
- [Javaslang](http://javaslang.com/) - better java 8 (this is really useful)

For testing 

- [Junit](http://junit.org/)
- [Truth](https://google.github.io/truth/)
