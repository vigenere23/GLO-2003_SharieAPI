[![Codacy Quality](https://api.codacy.com/project/badge/Grade/09636400336b4257bf58d2e1434f114e)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=glo2003/glo-2003-h19-equipe-16&amp;utm_campaign=Badge_Grade)
[![Codacy Coverage](https://api.codacy.com/project/badge/Coverage/09636400336b4257bf58d2e1434f114e)](https://www.codacy.com?utm_source=github.com&utm_medium=referral&utm_content=glo2003/glo-2003-h19-equipe-16&utm_campaign=Badge_Coverage)
[![Build Status](https://travis-ci.com/glo2003/glo-2003-h19-equipe-16.svg?token=rtBRz4eqbmPRnM1jJcqS&branch=master)](https://travis-ci.com/glo2003/glo-2003-h19-equipe-16)
[![Heroku](https://heroku-badge.herokuapp.com/?app=thawing-reef-71512&svg=1)](https://thawing-reef-71512.herokuapp.com/)

> *If Codacy's badges are not showing up, please take a look at their [status page](https://status.codacy.com/)*

# SharieAPI

Built with [spark](http://sparkjava.com/) framework for simplicity

## Installation (IntelliJ with Maven on Windows)

1. Download the following
    1. IntelliJ : <https://www.jetbrains.com/idea/download/#section=windows>
    2. Java SDK 1.8 : <https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>
    3. Maven : <http://mirror.dsrg.utoronto.ca/apache/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.zip>
2. Install IntelliJ
3. Install Java SDK 1.6 and Java SDK 1.8 and remember the installation paths
4. Install Maven
    1. Extract the files from the Maven download to a folder of your choice(remember the path)
    2. Press Win + R
    3. In the Run box type : rundll32.exe sysdm.cpl,EditEnvironmentVariables and Press Control + Shift before running a program and it'll run as admin
    4. Now edit the Path variable from the System variables list
    5. In the edit environment variable window click browse and select the file from where you extracted Maven, then Ok
5. Add JAVA_HOME environment variable(Maven needs it)
    1. If the environment variable window is close restart step `4.2` and `4.3`
    2. Press New under the system variables list
    3. In Variable name type : JAVA_HOME, then press Browse Directory and select the folder named jdk1.8.#_###(use path of step 3 for Java SDK 1.8), then Ok
6. Clone the project with this command : `git clone --recursive https://github.com/glo2003/glo-2003-h19-equipe-16`
    1. Open the cloned project in IntelliJ
    2. In IntelliJ press Ctrl + Shift + Alt + s
    3. In the SDK's menu press the + then +JDK and add the folder named jdk1.6.#_###(use path of step 3 for Java SDK 1.6), then Ok
7. Test the installation
    1. In IntelliJ use the terminal with the following command : `mvn clean install`
    2. In the same terminal type : `mvn exec:java`
    3. The server is started you can now use a web browser with the url <localhost:9090> ans see the following: Sharie API
8.  User Interface (Optional)
    1. Download Elm from here and install it : <https://guide.elm-lang.org/install.html>
    2. From UI folder use the following command : elm reactor
    3. The UI is started you can now use a web browser with the url <localhost:8000/src/Main.elm> ans see the UI for this project.

## Ajout de configurations IntelliJ

Le projet peut fonctionner avec une mémoire locale ou bien une base de donnée Mongo dans le cloud. Voici des configurations sur intelliJ pour exécuter le projet et les tests sur les deux types de sauvegarde.

1. Ajouter une configuration Application et entrer les données suivantes:
    1. Name: SharieApp DEV
    2. Main class: com.github.glo2003.SharieAPI
    3. Environment variables: 
        1. SHARIE_PROFILE : dev
        
2. Ajouter une configuration Application et entrer les données suivantes:
    1. Name: SharieApp PROD
    2. Main class: com.github.glo2003.SharieAPI
    3. Environment variables: 
        1. SHARIE_PROFILE : prod
        2. SHARIE_DATABASE_URL : mongodb://glo2003:mdp1234!@cluster0-shard-00-00-mawao.mongodb.net:27017,cluster0-shard-00-01-mawao.mongodb.net:27017,cluster0-shard-00-02-mawao.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true
        3. SHARIE_DATABASE_NAME : prodglo2003
        
3. Ajouter une configuration JUnit et entrer les données suivantes:
    1. Name: devTesting
    2. Test kind: Class
    3. Class: com.github.glo2003.controllers.ListingsControllerTest
    4. Environment variables: 
        1. SHARIE_PROFILE : dev
        
4. Ajouter une configuration JUnit et entrer les données suivantes:
    1. Name: onlineTesting
    2. Test kind: Class
    3. Class: com.github.glo2003.controllers.ListingsControllerTest
    4. Environment variables: 
        1. SHARIE_PROFILE : test
        2. SHARIE_DATABASE_URL : mongodb://glo2003:mdp1234!@cluster0-shard-00-00-jvq3c.mongodb.net:27017,cluster0-shard-00-01-jvq3c.mongodb.net:27017,cluster0-shard-00-02-jvq3c.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true
        3. SHARIE_DATABASE_NAME : testglo2003


## Add IntelliJ configurations for Maven

1. Install IntelliJ Maven plugin
    1. Go to `File > Settings > Plugins > Installed`, search for `Maven Integration`, check it and restart IntelliJ
    2. Once IntelliJ has restarted, click on `Enable auto-imports` when the popup appears
    3. Click on `Add configuration` between the `build` and the `run` icons
    4. Click on `+ > Maven`
        1. For the `Run` configuration, write `clean install exec:java` inside the field `Command line`
        2. For the `Test` configuration, write `clean install test`
        3. For the `Dependencies check` configuration, write `clean install dependency-check:check`
        4. For the `Coverage` configuration, write `clean test cobertura:cobertura codacy:coverage` inside the field `Command line`

> Note : test coverage results will be available at <https://app.codacy.com>

## Code checkstyle avec IntelliJ

1. Dans IntelliJ aller dans File > Close project
2. Vous êtes maintenant sur la page d'accueil, appuyer sur Configure > Plugins 
3. Dans recherche entrer CheckStyle-IDEA, insataller le plugin et redémarrer IntelliJ
4. Dans votre projet sur IntelliJ appuyer sur File > Settings > Checkstyle
5. Ajouter un configuration File du nom de votre choix et choisissez le fichier modifiedGoogleCheckStyle.xml, puis apply et Ok.

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

- [Jackson](http://wiki.fasterxml.com/JacksonHome)
- [Jackson-annotations](https://github.com/FasterXML/jackson-annotations)
- [SparkJava](http://sparkjava.com)

For testing 

- [Junit](http://junit.org/)
- [Truth](https://google.github.io/truth/)
- [REST-assured](http://rest-assured.io/) 
- [Hamcrest](http://hamcrest.org/JavaHamcrest/javadoc/2.1/)
