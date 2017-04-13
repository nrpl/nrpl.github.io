Effective Java
==============
This document sums up the advices of Joshua Bloch's Book Effective Java.
## Chapter 1: Introduction



## Chapter 2: Creating and Destroying objects
Item 1: Consider static factory methods instead of constructors
* static factory method returns instance of class
* advantages
  * factory methods are named, constructors not
  * construtors create new objects, factory methods can reuse objects
  * factory methods can return subtypes, construtors not
* disadvantages
  * classes without public/protected constructor can not be subclassed
  * factory methods are not distinguishable from other methods
  * not useful for many parameters (use builder, see Item 2)
* naming: valueOf(...), of(...), getInstance(), newInstance(), getType(), newType()

Item 2: Consider builder when faced with many constructor parameters
* avoid using telescoping constructor pattern (many constructors which call each other successively)
* java beans may be in inconsistent state partway through its construction (setter)
* Builder Pattern combines readability of Beans and safety of telescoping constructor
* Builder Pattern simulates names optional parameters
* Builder Pattern is useful for classes that would have many constructor/factory-method parameters
* Usage:
  ```
  WeatherConditions weatherConditions = WeatherConditions.Builder().temperature(23).humidity(40).cloudBase(3000).build();
  ```
* Implementation:
  ```
  public class WeatherConditions {
    private final int temperature;
    private final int humidity;
    private final int cloudBase;
    ...

    public static class Builder() {
      private int temperature = 0;
      private int humidity = 0;
      private int cloudBase = 0;

      public Builder temperature(int temperature) {
          this.temperature = temperature;
          return this;
      }

      ...

      public WeatherConditions build() {
        return new WeatherConditions(this);
      }
    }

    private WeatherConditions(Builder builder) {
      this.temperature = builder.temperature;
      ...
    }
  }
  ```

Item 3: Enforce the singleton property with a private constructor pr an enum type
* there are different approaches to implement singleton
* a single-element enum type is the best way!
  ```
  public enum MySingleton {
    INSTANCE;

    public void doSomething() {
      ...
    }
  }
  ```

Item 4: Enforce noninstantiability with a private constructor
* utility classes group static methods and fields and should not be instantiated
* prevent instantiation with a private constructor
* to prevent instantiation from the class itself throw an Exception in the constructor
* do not use abstract classes for this purpose

Item 5: Avoid creating unnecessary objects
* immutable objects should not be created using the constructor, instead the static factory should be used which reuses existent objects
* never use `new String("...")``
* consider moving local variables into fields which are initialized only once
* prefer primitives to boxed primitives and watch out for autoboxing

Item 6: Eliminate obsolete object references
* null out obsolete references when managing the memory, e.g. shrinking arrays, working with caches, listeners and callbacks
* but nulling out references should be the exception

Item 7: Avoid finalizers
* as a rule of thumb: avoid finalizers
* finalizers are not guaranteed to be executed promptly
* never do anything time-critical in a finalizer
  * do not close files in a finalizer
* never depend on a finalizer to update critical persistent state
* using finalizers causes severe performance penalties
* exceptions in finalizers do not print stacktraces, they just terminate silently
* do provide an explicit termination method and call this method after using the object
  * see close method of InputStream
*  use try-finally and call the close method in the finally block
* finalizers can be used as last resort to call the close method, but then they should log an warning to indicate this flaw

## Chapter 3: Methods Common to All Objects
TBD

## Chapter 8: General Programming
Item 45: Minimize Scope of local Variables
* Declare it where it is first used
* Initialize variables sensibly on declaration
* prefer for loops to while loops
* keep methods small and focused

Item 46: Prefer for-each loops to traditional for loops
* use for-each whenever possible
* implement iterable for your own types

Item 47: Know and use the libraries
* use expert knowledge and experience in practice by using standard libraries
* don't reinvent the wheel

Item 48: Avoid floar and double if exact answers are required
* float and double are primarily designed for engineering and scientific needs
* they are NOT designed for monetary calculations
* use BigDecimal, int or long for monetary calculations

Item 49: Prefer primitive types to boxed primitives
* applying the == operator on boxed primitives is almost always wrong
* auto-boxing and auto-unboxing cause performance penalties

Item 50: Avoid Strings where other types are more appropriate
* Strings are designed to represent textual values
* Strings are poor substitutes for other value types
* Strings are poor substitutes for enum types

Item 51: Beware the performance of String concatenation
* do not use the concatenation operator repeatedly
* n string concatenations requires time quadratic in n
* use StringBuilder or StringBuffer instead

Item 52: Refer to object by their interfaces
* If appropriate interface types exist, then parameters, return values, variables and fields should all be declared using interface types

Item 53: Prefer interfaces to reflection
* objects should not be accessed reflectively in normal programs at runtime
* create instances reflectively and access them normally via their interface or superclass

Item 54: Use native methods judiciously
* it is rarely advisable to use native methods for improved performance

Item 55: Optimize judiciously
* strive to write good programs rather than fast ones
* strive to avoid design decisions that limit performance
* consider the performance consequences of your API design decisions
* it is a very bad idea to warp an API to achieve good performance
* measure performance before and after each attempted optimization

Item 56: Adhere to generally accepted naming conventions
* never violate the [Java Language Specification](http://docs.oracle.com/javase/specs/) naming conventions without a good reason
* package names should be domain names in reverse order
* class, interface and enum: noun or noun phrase, first letter of each word capitalized, e.g. UserRepository
* method and field names: verb or verb phrase, first letter of first word in lowercase, first letter of each subsequent word uppercase, e.g. calculateDeviation
* methods that return a boolen should start with 'is', e.g. isValidSelection
* constants: uppercase letters separated by underscore, e.g. MIN_VALUE
