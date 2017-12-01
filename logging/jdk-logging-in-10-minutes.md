# Hands on: JDK logging in 10 minutes
## Intro
This hands on guide focusses on basic logging
with JDK logging which is part of JDK since Java 1.4.

## Prerequisites
- JDK 1.4 or higher
- editor of your choice

## Start with hello world
Create a class containing a main method which prints hello world to the console:
```java
public class HelloWorld {
    public static void main(String... args) {
        System.out.println("hello world");
    }
}
```

## Create a logger
A logger is required to send log messages.
Use the provided factory method to create one:
```java
import java.util.logging.Logger;

public class HelloWorld {
    private static final Logger log = Logger.getLogger(HelloWorld.class.getName());

    public static void main(String... args) {
        System.out.println("hello world");
    }
}
```
Note that you pass in the fully qualified class name.
Technically you could pass in any String,
but the fully qualified name is used for external configuration.

## Replace System.out.println by log.log()
Now you can use the info method to send a log message:
```java
import java.util.logging.Logger;
import java.util.logging.Level;

public class HelloWorld {
    private static final Logger log = Logger.getLogger(HelloWorld.class.getName());

    public static void main(String... args) {
        log.log(Level.INFO, "hello world");
    }
}
```
Compile and run the application and you will see the log statements on the terminal:
```sh
INFO: hello world
```
By default JDK logging sends messages to the console.
You can change this behaviour and specify different handlers, for example
to log into a file.

## Log Level
As you have noticed, you have to pass in a log level.
Log levels are a basic concept of logging.
They are used to define the importance of messages
and allow to configure  which information is being logged.

The following levels are available:
- SEVERE (highest value)
- WARNING
- INFO
- CONFIG
- FINE
- FINER
- FINEST (lowest value)

You can use the log method to specify the level:
```java
log.log(Level.INFO, "this is a info log message");
log.log(Level.SEVERE, "this is a severe log message");
```

Or you can use convenient methods which correspond to a level:
```java
import java.util.logging.Logger;
import java.util.logging.Level;

public class HelloWorld {
    private static final Logger log = Logger.getLogger(HelloWorld.class.getName());
    public static void main(String... args) {
        log.severe("severe");
        log.warning("warning");
        log.info("info");
        log.config("config");
        log.fine("fine");
        log.finer("finer");
        log.finest("finest");
    }
}
```
If you compile and run the code, you can see, that not all messages are printed:
```
SEVERE: severe
WARNING: warning
INFO: info
```
But why?
The logger itself is initialised with a log level.
By default it is INFO, which means that all messages with log level
INFO or higher or accepted by the logger.
Messages with a lower level are ignored.

You can set the log level of the logger programatically:
```java
import java.util.logging.Logger;
import java.util.logging.Level;

public class HelloWorld {
    private static final Logger log = Logger.getLogger(HelloWorld.class.getName());
    public static void main(String... args) {
        log.setLevel(Level.SEVERE);

        log.severe("severe");
        log.warning("warning");
        log.info("info");
        log.config("config");
        log.fine("fine");
        log.finer("finer");
        log.finest("finest");
    }
}
```
The logger now excepts only messages of the highest level SEVERE:
```
SEVERE: severe
```

Now we would like to log everything:
```java
...
log.setLevel(Level.ALL);
...
```
Which outputs
```
SEVERE: severe
WARNING: warning
INFO: info
```
If you analyse the output, you should be surprised!
Not all of the messages are visible.

The reason is, that not only the logger filters messages.
The logger passes the messages to appenders which are responsible for printing the messages.
In our case a console handler was created behind the scenes and initialised with the INFO level by default.

That means the logger passes all messages to the handler,
but the handler filters out all messages lower than INFO.

You can programatically check the level of a handler:
```java
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;

public class HelloWorld {
    private static final Logger log = Logger.getLogger(HelloWorld.class.getName());
    public static void main(String... args) {
        log.setLevel(Level.SEVERE);

        Handler handler = new ConsoleHandler();
        log.info("The handlers log level is:" handler.getLevel() +" by default");

        log.severe("severe");
        log.warning("warning");
        log.info("info");
        log.config("config");
        log.fine("fine");
        log.finer("finer");
        log.finest("finest");
    }
}
```
which outputs
```
...
INFO: The handlers log level is: <INFO> by default
...
```

We can create a handler and set the log level to ALL:
```java
...
Handler handler = new ConsoleHandler();
handler.setLevel(Level.ALL);
log.addHandler(handler);
...
```

Now all the messages show up:
```
SEVERE: severe
WARNING: warning
INFO: info
CONFIG: config
FINE: fine
FINER: finer
FINEST: finest
```

## External Configuration
To externalise the configuration create a file named logging.properties with the following content:
```
.level=ALL
handlers=java.util.logging.ConsoleHandler, java.util.logging.FileHandler
java.util.logging.ConsoleHandler.level=ALL
java.util.logging.FileHandler.level=INFO
java.util.logging.FileHandler.pattern=demo.log
java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter
```
As you can see, we configure a second appender and set the levels within the properties file.
Remove the programatic configuration in the java class:
```java
import java.util.logging.Logger;
import java.util.logging.Level;

public class HelloWorld {
    private static final Logger log = Logger.getLogger(HelloWorld.class.getName());

    public static void main(String... args) {
      log.severe("severe");
      log.warning("warning");
      log.info("info");
      log.config("config");
      log.fine("fine");
      log.finer("finer");
      log.finest("finest");
    }
}
```

Compile and run the class and pass the configuration file:
```
java -Djava.util.logging.config.file=logging.properties HelloWorld
```
Additional to the log output on console you can find the specified log file
containing all messages with a level of INFO or higher.
```
SEVERE: severe
WARNING: warning
INFO: info
```

## See also
- [Logging Overview](./logging-overview)
