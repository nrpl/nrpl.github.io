Project Structure
=================
The project structure is an important key point for a software project.
The first impression of the actual state and quality of a project is based on the project structure
which you recognize after checking out the sources from VCS.
If the project structure looks like a mess and contains a bunch of unorganised files and folders,
you expect the same chaos in the code.
But if it is logically structured and well organised, you can start to explore the project with ease.

Technically the project structure is just a hierarchy of files and folders.
This structure could be created manually using the standard tools of the operating system.
A more comfortable and less error-prone approach is to use an IDE or build tool which can be used to generate a default structure for a new project.

I prefer to use a build tool like Maven or Gradle to generate a standardised project structure without any dependencies to a specific IDE.
The generated project can be imported into an IDE of choice or even be edited using a simple texteditor.

Maven and Gradle use an opinionated approach and assume that you follow the conventions and use their default project structure.
The disadvantage of this approach is reduced flexibility, but the big advantage is standardisation and less configuration.
It is still possible, but not recommended, to add specific configuration to the build files to use your own structure.

As always, it is best, to stick to the standards and use the default structure defined by the build tools.
The tools are proven in use and based on best practices used by many developers in countless projects around the world.

If you are using Ant, you have to define the project structure on your own.
You could script an ant target to generate a structure for you, but there is no generation out of the box.
It is still best to use a well known and proven project structure instead of fleshing out your own.

An important rule of thumb is to store different types of files or data in separated paths:
- code sources
- test code sources
- generated files
- 3rd Party Libraries
- configuration files
- binary files like images
- documentation

Maven
-----
To generate a new project with Maven the [archetype plugin](https://maven.apache.org/archetypes/index.html) can be used.
There are several archetypes available to generate different types of projects.
An archetype is a project template for a specific type of project, e.g. Webapplication or Maven Plugin.

The following command uses the generate goal of the archetype plugin to generate a project based on the Quickstart archetype.
```
mvn archetype:generate -DgroupId=de.nrpl -DartifactId=my-sample-app -DinteractiveMode=false
```

By default the Quickstart archetype is used which creates a sample Maven Project.
The artifactId represents the project name.
The groupId defines the namespace which is used to identify the application in a maven repository.
The groupId has to follow packagename rules as described in the [maven naming conventions](https://maven.apache.org/guides/mini/guide-naming-conventions.html).

Alternatively the following command can be used for an interactive project creation.
Maven archetype will prompt for user input to select the archetype and specify the paramters.
```
maven archetype:generate
```
A list of the maven archetypes can be found [here](http://maven.apache.org/archetypes/).
It is possible to [create your own Archetypes](https://maven.apache.org/guides/mini/guide-creating-archetypes.html).

To build the project use
```
mvn install
```

The resulting structure is illustrated below:
```
sample-application/
├── pom.xml
├── src
│   ├── main
│   │   └── java
│   │       └── de
│   │           └── nrpl
│   │               └── App.java
│   └── test
│       └── java
│           └── de
│               └── nrpl
│                   └── AppTest.java
└── target
    ├── classes
    │   └── de
    │       └── nrpl
    │           └── App.class
    ├── maven-archiver
    │   └── pom.properties
    ├── maven-status
    │   └── maven-compiler-plugin
    │       ├── compile
    │       │   └── default-compile
    │       │       ├── createdFiles.lst
    │       │       └── inputFiles.lst
    │       └── testCompile
    │           └── default-testCompile
    │               ├── createdFiles.lst
    │               └── inputFiles.lst
    ├── sample-application-1.0-SNAPSHOT.jar
    ├── surefire-reports
    │   ├── TEST-de.nrpl.AppTest.xml
    │   └── de.nrpl.AppTest.txt
    └── test-classes
        └── de
            └── nrpl
                └── AppTest.class
```

### Additional Information
- [Maven Project Website](http://maven.apache.org/)
- [Maven Getting started - Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Spring.io Getting started - Building Java Projects with Maven](https://spring.io/guides/gs/maven/)
- [Maven Archetype Introduction](https://maven.apache.org/archetype/index.html)
- [Maven Archetype Plugin](https://maven.apache.org/archetype/maven-archetype-plugin/index.html)


Gradle
------
Project creation with gradle is performed using the [build init plugin](https://docs.gradle.org/current/userguide/build_init_plugin.html).
The plugin supports several project types.
In contrast to maven gradle does not generate the project root directory.
We have to do that manually.
A basic java project can be generated using the following commands:
```
mkdir sample-application && cd sample-application
gradle init --type=java-application
```

The project can be build and started using
```
gradle run
```

The init plugin adds the gradlewrapper to the project.
The gradle wrapper is a gradle installation within the project directory.
This makes it possible to checkout and build the code without the need of a gradle installation (it is already included in the project).
To build the project using the gradle wrapper use the following command:
```
./gradlew run
```

The resulting project structure is illustrated below:
```
sample-application/
├── build
│   ├── classes
│   │   └── main
│   │       └── App.class
│   └── tmp
│       └── compileJava
│           └── emptySourcePathRef
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   └── java
    │       └── App.java
    └── test
        └── java
            └── AppTest.java
```

### Additional Information
- [Gradle Project Website](https://gradle.org/)
- [Spring.io Getting started - Building Java Projects with Gradle](https://spring.io/guides/gs/gradle/)


Project Documentation
---------------------
Each project should provide at least basic documentation.
A best practice is to add a readme.md on project root containing a short description of the project and basic information to get a functional overview.
Maven provides the [Maven Site Plugin](https://maven.apache.org/plugins/maven-site-plugin/) to generate a Project Website based on documentation included in the project.
