# Logging Overview
Logging, besides debugging, can be used to get an insight to an application.
In contrast to debugging, the source code of the application has to be enriched with log statements in order to generate log messages.

The purpose of a logging framework is to write log messages to a defined output channel.
These messages are used to gather information about the internal state, the program flow and interactions of a running software system.
Logging is crucial to analyse problems and erros during the runtime of an application.

The simplest form of logging is to write messages to System.out or System.err as shown in the following snippet.
```java
    ...
    System.out.println("foobar");
    ...
```
This method is acceptable for simple demo applications and prototypes, but not for professional applications used in production.
There are several problems using this approach:
- no support for log levels
- log messages are printed on the console/terminal, but are not persisted for afterwards analysis
- the console/terminal which is used by default for System.out/System.err is not always accessible (e.g. server systems / distributed systems)
- logging to the console/terminal exposes log messages directly to the user

Logging frameworks solve these problems and provide a proven and tested solution for common logging needs.
Features of logginframeworks are:
- Loglevels
- Possibility to use various output channels, e.g. files, stdout, database, etc.
- Logrotation and archiving of logfiles

Since Java 1.4 a basic logging solution is part of the framework which can be found in the package
[java.util.logging](https://docs.oracle.com/javase/9/docs/api/java/util/logging/package-summary.html).

The de facto standard logging framework in the java world is [Log4J](https://logging.apache.org/log4j) which is more powerful than JDK logging and highly configurable.
The successor of Log4J is [logback](https://logback.qos.ch/).

To ensure exchangeability of the logging framework it is recommended to use a logging facade.
A logging facade acts as abstraction for various logging frameworks.
It is used in the application code to implement logging and delegates to a logging framework.
This approach allows to change the logging framework without the need of adjusting the initialization of a logger and changing log statements.

[JCL](https://commons.apache.org/proper/commons-logging/) is such a logging facade.
It steps in the classloading process to dynamically find and initialize a logging framework from the classpath.
This dynamic discovery process is known as a weakness of the framework, because it introduces complexity and is a source of bugs.
See [think again](https://articles.qos.ch/thinkAgain.html) for more information.

The modern alternative to JCL is [SLF4J](https://www.slf4j.org).
In contrast to JCL it uses a static discovery process to determine a logging framework.
Please refer to [Simplifying distinction betweeen slf4j](http://jayunit100.blogspot.de/2013/10/simplifying-distinction-between-sl4j.html)
for an brief description of the differences.
SLF4J can be used with various logging frameworks.
JDK logging, log4j and logback are supported.

## See also
- [JDK logging in 10 minutes](./jdk-logging-in-10-minutes)
