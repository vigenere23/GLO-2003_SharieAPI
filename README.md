<img style="width:100%;" src="/github-banner.png">

# starter-kit-java

Java starter kit for the glo2003 laboratory at Ulaval.

Built with [spark](http://sparkjava.com/) framework for simplicity

Starting at java is not easy: [better-java](https://github.com/cxxr/better-java) gives you modern best practices.

Some nice libraries have been added to help you start and do things way faster and more easily. Have a look at them
in the [build.gradle](/build.gradle) file.

## Installation (working with IntelliJ on Windows)
1. Download the following
<br />IntelliJ : https://www.jetbrains.com/idea/download/#section=windows
<br />Java SDK 1.6 : https://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase6-419409.html
<br />Java SDK 1.8 : https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
<br />Maven : http://mirror.dsrg.utoronto.ca/apache/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.zip

2. Install IntelliJ

3. Install Java SDK 1.6 and Java SDK 1.8 and remember the installation paths

4. Install Maven
<br />4.1 Extract the files from the Maven download to a folder of your choice(remember the path)
<br />4.2 Press Win + R
<br />4.3 In the Run box type : rundll32.exe sysdm.cpl,EditEnvironmentVariables
and Press Control + Shift before running a program and it'll run as admin
<br />4.4 Now edit the Path variable from the System variables list
<br />4.5 In the edit environment variable window click browse and select the file from where you extracted Maven, then Ok

5. Add JAVA_HOME environment variable(Maven needs it)
<br />5.1 If the environment variable window is close restart step 4.2 and 4.3
<br />5.2 Press New under the system variables list
<br />5.3 In Variable name type : JAVA_HOME
then press Browse Directory and select the folder named jdk1.8.#_###(use path of step 3 for Java SDK 1.8), then Ok

6. Clone the project with this command : git clone --recursive https://github.com/glo2003/glo-2003-h19-equipe-16
<br />6.1 Open the cloned project in IntelliJ
<br />6.2 In IntelliJ press Ctrl + Shift + Alt + s
<br />6.3 In the SDK's menu press the + then +JDK and add the folder named jdk1.6.#_###(use path of step 3 for Java SDK 1.6), then Ok

7. Test the installation
<br />7.1 In IntelliJ use the terminal with the following command : mvn clean install
<br />7.2 In the same terminal type : mvn exec:java
<br />7.3 The server is started you can now use a web browser with the url localhost:9090 ans see the following: Sharie API

8. User Interface (Optional)
<br />8.1 Download Elm from here and install it : https://guide.elm-lang.org/install.html
<br />8.2 From UI folder use the following command : elm reactor
<br />8.3 The UI is started you can now use a web browser with the url localhost:8000/src/Main.elm ans see the UI for this project.


## Building with gradle

```bash
./gradlew build
```

## Running with gradle

```bash
./gradlew run
```

## Building with maven

```bash
mvn clean install
```

## Running with maven

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
